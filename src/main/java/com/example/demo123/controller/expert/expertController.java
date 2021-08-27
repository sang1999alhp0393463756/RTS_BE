package com.example.demo123.controller.expert;

import com.example.demo123.dto.request.*;
import com.example.demo123.dto.response.*;

import com.example.demo123.entity.*;
import com.example.demo123.repository.*;
import com.example.demo123.service.AmazonClient;
import com.example.demo123.service.UploadExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@PreAuthorize("hasRole('EXPERT') or hasRole('ADVISOR') or hasRole('ADMIN')")
@RequestMapping("/expert")
public class expertController {
        private AmazonClient amazonClient;
    @Autowired
    expertController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
//        return this.amazonClient.uploadFile(file);
//    }
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    ExamRepository examRepository;
    @Autowired
    QueryRepository queryRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    private UploadExcelUtils uploadExcelUtils;

    // Course CRUD

    @GetMapping("/allCourseExpertCreate/{id}")
    public ResponseEntity<?> showCourseExpertCreate(@PathVariable long id) {
        List<Course> list = courseRepository.courseExpertCreate(id);
        List<course> coursesList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            course course = new course();
            course.setId(list.get(i).getId());
            course.setContent(list.get(i).getContent());
            course.setThumbnail(list.get(i).getThumbnail());
            course.setTitle(list.get(i).getTitle());
            course.setSortDescription(list.get(i).getSortDescription());
            course.setCoreExpert(list.get(i).getCoreExpert());
            course.setPrice(list.get(i).getPrice());
            course.setRatingToltal(list.get(i).getRatingToltal());
            course.setCategoryId(list.get(i).getCategory().getId());
            course.setCategoryName(list.get(i).getCategory().getName());
            course.setCreateDate(list.get(i).getCreatedDate());
            course.setStatus(list.get(i).getStatus());
            course.setCreatedBy(list.get(i).getCreatedBy());
            course.setDate_duyet(list.get(i).getDate_duyet());
            course.setEmail_duyet(list.get(i).getEmail_duyet());
            course.setSale(list.get(i).getSale());
            coursesList.add(course);
        }
        return ResponseEntity.ok(coursesList);
    }
    @PostMapping("/addCourse")
    public ResponseEntity<?> saveCourse(@ModelAttribute courseRequest courseRequest) {
        Category category = categoryRepository.findById(courseRequest.getCategory_id()).orElseThrow(() -> new UsernameNotFoundException("Category Not Found with id: " + courseRequest.getCategory_id()));
        Course course = new Course();
        course.setContent(courseRequest.getContent());
        course.setCoreExpert(courseRequest.getCoreExpert());
        course.setThumbnail(this.amazonClient.uploadFile(courseRequest.getThumbnail()));
        course.setTitle(courseRequest.getTitle());
        course.setSortDescription(courseRequest.getSortDescription());
        course.setPrice(courseRequest.getPrice());
        course.setCategory(category);
        course.setSale(courseRequest.getSale());
        course.setStatus("pending");
        courseRepository.save(course);
        return ResponseEntity.ok(courseRequest);
    }
    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable long id, @ModelAttribute updateCourseRequest course) {
        Course courseOptional = courseRepository.getById(id);
        Category category = categoryRepository.getById(course.getCategory_id());
        if(courseOptional!=null){
            courseOptional.setContent(course.getContent());
            courseOptional.setTitle(course.getTitle());
            courseOptional.setThumbnail(course.getThumbnail());
            courseOptional.setCategory(category);
            courseOptional.setCoreExpert(course.getCoreExpert());
            courseOptional.setPrice(course.getPrice());
            courseOptional.setSale(course.getSale());
            courseOptional.setSortDescription(course.getSortDescription());
            courseRepository.save(courseOptional);
            return ResponseEntity.ok("update success");
        }else {
            return  ResponseEntity.ok("update fail");
        }
    }

    @PutMapping("/deleteCourse/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        Course courseOptional = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Course Not Found with id: " + id));
        if (courseOptional!=null){
            try {
                courseOptional.setStatus("pending");
                courseRepository.save(courseOptional);
                return ResponseEntity.ok(courseOptional);
            }catch (NullPointerException e){
                return ResponseEntity.ok(e.toString());
            }

        }else {
            return ResponseEntity.ok("fail");
        }
    }

    @PutMapping("activeCourse/{id}")
    public ResponseEntity<?> activeCourse(@PathVariable long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Course Not Found "));
        if (course!=null){
            course.setStatus("active");
            courseRepository.save(course);
            return ResponseEntity.ok("success");
        }else {
            return ResponseEntity.ok("fail");
        }
    }
    @GetMapping("/getUserByCourse/{id}")
    public ResponseEntity<User> getUserByCourse(@PathVariable long id) {
        Optional<User> userOptional = userRepository.getInfoUserByCourse(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Exam CRUD

    @PutMapping("/deleteExam/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable long id){
        try {
            Exam exam = examRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Exam Not Found"));
            exam.setStatus("pending");
            examRepository.save(exam);
            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.ok(e.toString());
        }
    }
    @PutMapping("activeExam/{id}")
    public ResponseEntity<?> activeExam(@PathVariable long id){
        Exam exam = examRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Exam Not Found "));
        if (exam!=null){
            exam.setStatus("active");
            examRepository.save(exam);  
            return ResponseEntity.ok("success");
        }else {
            return ResponseEntity.ok("fail");
        }
    }

    // Lesson CRUD
    @GetMapping("/findLessonByCourseId/{idCourse}")
    public ResponseEntity<?> getLessonByIdCourse(@PathVariable long idCourse) {
        List<Lesson> lesson = lessonRepository.findLessonByCourseId(idCourse);
        Course course = courseRepository.getById(idCourse);
        List<LessonResponse> lessonList1 = new ArrayList<>();

        for (int i = 0; i < lesson.size(); i++) {
            LessonResponse lessonCustom = new LessonResponse();
            lessonCustom.setId(lesson.get(i).getId());
            lessonCustom.setContent(lesson.get(i).getContent());
            lessonCustom.setTitle(lesson.get(i).getTitle());
            lessonCustom.setImage(lesson.get(i).getImage());
            lessonCustom.setLinkVideo(lesson.get(i).getLinkVideo());
            lessonCustom.setCourseid(idCourse);
            lessonCustom.setCreatedDate(lesson.get(i).getCreatedDate());
            lessonCustom.setCreatedBy(lesson.get(i).getCreatedBy());
            lessonCustom.setModifiedBy(lesson.get(i).getModifiedBy());
            lessonCustom.setModifiedDate(lesson.get(i).getModifiedDate());
            lessonCustom.setStatus(lesson.get(i).getStatus());
            lessonCustom.setShortDescription(lesson.get(i).getShortDescription());
            lessonList1.add(lessonCustom);
        }
        return ResponseEntity.ok(lessonList1);
    }
    @GetMapping("/findLessonByLessonId/{id}")
    public ResponseEntity<?> getLessonById(@PathVariable long id) {
        Optional<Lesson> lesson = lessonRepository.findLessonByLessonId(id);
        LessonResponse lessonCustom = new LessonResponse();
        lessonCustom.setId(lesson.get().getId());
        lessonCustom.setContent(lesson.get().getContent());
        lessonCustom.setTitle(lesson.get().getTitle());
        lessonCustom.setImage(lesson.get().getImage());
        lessonCustom.setLinkVideo(lesson.get().getLinkVideo());
        lessonCustom.setCourseid(id);
        lessonCustom.setCreatedDate(lesson.get().getCreatedDate());
        lessonCustom.setCreatedBy(lesson.get().getCreatedBy());
        lessonCustom.setModifiedBy(lesson.get().getModifiedBy());
        lessonCustom.setModifiedDate(lesson.get().getModifiedDate());
        lessonCustom.setStatus(lesson.get().getStatus());
        lessonCustom.setShortDescription(lesson.get().getShortDescription());
        return ResponseEntity.ok(lessonCustom);
    }
    @PostMapping("/addLesson")
    public ResponseEntity<?> createNewLesson(@ModelAttribute LessonRequest lessonRequest) {
        Course course = courseRepository.getById(lessonRequest.getCourseID());

            Lesson lesson = new Lesson();
            lesson.setContent(lessonRequest.getContent());
            lesson.setLinkVideo(lessonRequest.getLinkVideo());
            lesson.setTitle(lessonRequest.getTitle());
            lesson.setCourse(course);
            lesson.setImage(this.amazonClient.uploadFile(lessonRequest.getImage()));
            lesson.setStatus("active");
            lesson.setShortDescription(lessonRequest.getShortDescription());
            lessonRepository.save(lesson);
            return ResponseEntity.ok(lessonRequest);

    }
    @PutMapping("/updateLesson/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable long id,
                                          @ModelAttribute LessonUpdateRequest lesson) {
        Lesson lesson1 = lessonRepository.findLessonById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with lesson"));
        Course course = courseRepository.getById(lesson.getCourseID());
        lesson1.setTitle(lesson.getTitle());
        lesson1.setContent(lesson.getContent());
        lesson1.setImage(lesson.getImage());
        lesson1.setCourse(course);
        lesson1.setLinkVideo(lesson.getLinkVideo());
        lesson1.setStatus(lesson.getStatus());
        lesson1.setShortDescription(lesson.getShortDescription());
        lessonRepository.save(lesson1);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/deleteLesson/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable long id) {
        try {
            Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Lesson Not Found"));
//            reportRepository.deleteBlog_reports(id);
            lesson.setStatus("pending");
            lessonRepository.save(lesson);

            return ResponseEntity.ok("success");
        }catch (Exception e){
            return ResponseEntity.ok(e.toString());
        }
    }

//CRUD exam
@GetMapping("examListOfExpert/{courseId}")
public ResponseEntity<?> getExamListByCourse(@PathVariable Long courseId){
        List<Exam> examList = examRepository.getAllByCourse(courseId);
        return ResponseEntity.ok(examList);
}

    @GetMapping("/getExamDetailByExpert/{id}")
    public ResponseEntity<?> getExamById(@PathVariable long id) {
        Optional<examRespon> exam = examRepository.findExamDetail(id);
            return ResponseEntity.ok(exam);
    }

    @PostMapping("/addQuestion")
    public ResponseEntity<?> addQuestion(@ModelAttribute examRequest examRequest)  {
        try{
            Course course = courseRepository.getById(examRequest.getCourseId());
            Exam exam1 = new Exam();
                exam1.setCourse(course);
                exam1.setStatus("active");
                exam1.setTitle(examRequest.getTitle());
                exam1.setSecurity(examRequest.getSecurity());
                exam1.setDescription(examRequest.getDescription());
                examRepository.save(exam1);
                ByteArrayInputStream bis = new ByteArrayInputStream(examRequest.getFile().getBytes());
                List<question> list = uploadExcelUtils.importAssetFromInputStream(bis);
                if (list != null && list.size() > 0) {
                    for (int i=0;i<list.size();i++) {
                        list.get(i).setExam(exam1);
                    }
                    questionRepository.saveAll(list);
                }
                return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping("/updateExam")
    public ResponseEntity<?> updateExam(@ModelAttribute updateExam exam) {
        try{
            Exam exam1 = examRepository.getById(exam.getExamID());
            if(exam1!=null) {
                exam1.setTitle(exam.getTitle());
                exam1.setStatus(exam.getStatus());
                exam1.setSecurity(exam.getSecurity());
                exam1.setDescription(exam.getDescription());
                examRepository.save(exam1);
                questionRepository.DeleteByExam(exam.getExamID());
                ByteArrayInputStream bis = new ByteArrayInputStream(exam.getFile().getBytes());
                List<question> list = uploadExcelUtils.importAssetFromInputStream(bis);
                if (list != null && list.size() > 0) {
                    for (int i=0;i<list.size();i++) {
                        list.get(i).setExam(exam1);
                    }
                    questionRepository.saveAll(list);
                }
                return ResponseEntity.ok("success");
            }else {
                return ResponseEntity.ok("course chưa được active!");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("activeLesson/{id}")
    public ResponseEntity<?> activeLesson(@PathVariable long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Lesson Not Found "));
        if (lesson!=null){
            lesson.setStatus("active");
            lessonRepository.save(lesson);
            return ResponseEntity.ok("success");
        }else {
            return ResponseEntity.ok("fail");
        }
    }


    @GetMapping("/downloadExcelFormQuestion")
    public ResponseEntity<Object> downloadExcelFormQuestion(@RequestParam("nameFile") String nameFile) throws IOException
    {
        String filename = ".\\upload-dir\\Question\\";
        File file = new File(filename+nameFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
    // CRUD Document


    @PostMapping("addDocument")
    public ResponseEntity<?> addDocument(@ModelAttribute examRequest exam)  {

        try {
            document document = new document();
            Course course = courseRepository.getById(exam.getCourseId());
                document.setCourse(course);
                document.setStatus("active");
                document.setTitle(exam.getTitle());
                document.setSecurity(exam.getSecurity());
                document.setDescription(exam.getDescription());
                document.setDocumentName(this.amazonClient.uploadFile(exam.getFile()));
                documentRepository.save(document);
                return ResponseEntity.ok(document);
        }catch (Exception e){
            return ResponseEntity.ok(e);
        }
    }

    @PostMapping("updateDocument")
    public ResponseEntity<?> updateDocument(@ModelAttribute documentRequest exam)  {

        try {
            document document = documentRepository.findById(exam.getDocumentId()).orElseThrow(() -> new UsernameNotFoundException("not found"));
            if(document !=null){
                document.setStatus("active");
                document.setTitle(exam.getTitle());
                document.setSecurity(exam.getSecurity());
                document.setDescription(exam.getDescription());
                document.setDocumentName(exam.getFile());
                documentRepository.save(document);
                return ResponseEntity.ok(document);
            }else {
                return ResponseEntity.ok(document);
            }
        }catch (Exception e){
            return ResponseEntity.ok(e);
        }
    }

    @PutMapping("updateStatusDocument/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id,@RequestParam(name = "status") String status){
        document document = documentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("not found"));
        if(document!=null){
            document.setStatus(status);
            documentRepository.save(document);
            return ResponseEntity.ok(document);
        }else {
            return ResponseEntity.ok("document not found!");
        }
    }
    @GetMapping("getDocumentByCourseID")
    public ResponseEntity<?> getNameDocument(@RequestParam(name = "courseId") Long courseId){
            List<document> list2 = documentRepository.getAllByCourse(courseId);
            return ResponseEntity.ok(list2);
    }
    //payment
    //tạo màn hình bên expertDashboard bao gồm :
    //tên user(gửi về id user)
    // option chọn course rút tiền(gửi về id course đó)
    //số tiền cần rút
    //mã code, cái này ko phải expert nhập mà fe dùng hàm random để sinh ra cho nó 1 đoạn mã,ko thể sửa
    //stk
    //tên ngân hàng
    @PostMapping("requestPayment")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentRequest payment){
        payment payment1 = new payment();
        payment1.setUserId(payment.getUserId());
        payment1.setCourseId(payment.getCourseId());
        payment1.setCode(payment.getCode());
        payment1.setNameBank(payment.getNameBank());
        payment1.setMoney(payment.getMoney());
        payment1.setStk(payment.getStk());
        payment1.setStatus("pending");
        paymentRepository.save(payment1);
        return ResponseEntity.ok("success");
    }
    //api này để get ra lịch sử yêu cầu rút tiền và lịch sử những yêu cầu đã đc thanh toán
    //tạo hẳn 1 menu lịch sử giao dịch(bao gồm 2 tab dưới đây)
    //chế độ pending là lịch sử yêu cầu(date lấy trường created_date)
    //chế độ active là lịch sử đã được thanh toán(date lấy trường modified_date)
    @GetMapping("getRequestPayment")
    public ResponseEntity<?> getRequestPayment(@RequestParam(name = "userId") Long userId,@RequestParam(name = "status") String status){
        List<PaymentRespon> list = paymentRepository.getAllByStatusOfExpert(status,userId);
        return ResponseEntity.ok(list);
    }
}
