package mii.mcc72.ams_server_app.utils;

import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.User;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"id", "first_name", "last_name", "phone_number", "email", "username", "password", "is_enabled"};
    static String SHEET = "Tutorials";


    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<User> excelToUsers(InputStream is) {
        try {
            Workbook workbook = WorkbookFactory.create(is);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<User> users = new ArrayList<User>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                org.apache.poi.ss.usermodel.Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Employee employee = new Employee();
                User user = new User();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    DataFormatter df = new DataFormatter();
                    switch (cellIdx) {
                        case 0:
                            break;

                        case 1:
                            employee.setFirstName(df.formatCellValue(currentCell));
                            break;

                        case 2:
                            employee.setLastName(df.formatCellValue(currentCell));
                            break;

                        case 3:
                            employee.setPhoneNumber(df.formatCellValue(currentCell));
                            break;
                        case 4:
                            user.setEmail(df.formatCellValue(currentCell));
                            break;
                        case 5:
                            user.setUsername(df.formatCellValue(currentCell));
                            break;
                        case 6:
                            user.setPassword(df.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                user.setEmployee(employee);
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
