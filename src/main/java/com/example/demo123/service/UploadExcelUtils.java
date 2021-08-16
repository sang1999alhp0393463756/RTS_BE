package com.example.demo123.service;

import com.example.demo123.entity.question;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadExcelUtils {

    public List<question> importAssetFromInputStream(InputStream is) throws IOException {
        List<question> ret = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(is);
        Sheet dataTypeSheet = workbook.getSheetAt(0);

        int rowIndex = 1;
        int falseIndex = 1;
        int num = dataTypeSheet.getLastRowNum();

        while (rowIndex <= num) {
            Row currentRow = dataTypeSheet.getRow(rowIndex);
            Cell currentCell = null;
            if (currentRow != null) {
                question entity = new question();

                Integer index = 0;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        Integer number = (int) currentCell.getNumericCellValue();
                        if (number != null) {
                            entity.setNumber(number);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        Integer number = Integer.valueOf(currentCell.getStringCellValue());
                        if (number != null) {
                            entity.setNumber(number);
                        }
                    }
                }

                index = 1;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String question1 = String.valueOf(currentCell.getStringCellValue());
                        if (question1 != null) {
                            entity.setQuestion(question1);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String question1 = String.valueOf(currentCell.getNumericCellValue());
                        if (question1 != null) {
                            entity.setQuestion(question1);
                        }
                    }
                }

                index = 2;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String option1 = String.valueOf(currentCell.getStringCellValue());
                        if (option1 != null) {
                            entity.setOption1(option1);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String option1 = String.valueOf(currentCell.getNumericCellValue());
                        if (option1 != null) {
                            entity.setOption1(option1);
                        }
                    }
                }

                index = 3;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String option2 = String.valueOf(currentCell.getStringCellValue());
                        if (option2 != null) {
                            entity.setOption2(option2);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String option2 = String.valueOf(currentCell.getNumericCellValue());
                        if (option2 != null) {
                            entity.setOption2(option2);
                        }
                    }
                }

                index = 4;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String option3 = String.valueOf(currentCell.getStringCellValue());
                        if (option3 != null) {
                            entity.setOption3(option3);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String option3 = String.valueOf(currentCell.getNumericCellValue());
                        if (option3 != null) {
                            entity.setOption3(option3);
                        }
                    }
                }

                index = 5;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String option4 = String.valueOf(currentCell.getStringCellValue());
                        if (option4 != null) {
                            entity.setOption4(option4);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String option4 = String.valueOf(currentCell.getNumericCellValue());
                        if (option4 != null) {
                            entity.setOption4(option4);
                        }
                    }
                }

                index = 6;
                if (index != null) {
                    currentCell = currentRow.getCell(index);
                    if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
                        String answer = String.valueOf(currentCell.getStringCellValue());
                        if (answer != null) {
                            entity.setCorrectanswer(answer);
                        }
                    } else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        String answer = String.valueOf(currentCell.getNumericCellValue());
                        if (answer != null) {
                            entity.setCorrectanswer(answer);
                        }
                    }
                }
                ret.add(entity);
            }
            rowIndex++;
        }
        return ret;
    }
}
