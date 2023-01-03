package mii.mcc72.ams_server_app.config;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.*;
import mii.mcc72.ams_server_app.repos.*;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class DataDummyGenerator implements CommandLineRunner {
    private final PrivilegeRepo privilegeRepository;
    private final RoleRepo roleRepository;
    private final CategoryRepo categoryRepo;
    private final UserRepository userRepository;
    private final DepartmentRepo departmentRepo;
    private final AssetRepo assetRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmployeeRepo employeeRepo;


    @Override
    public void run(String... args) throws Exception {
        ArrayList<Role> listRole = new ArrayList<>();
        Role roleEmployee = new Role(1, "EMPLOYEE", null, null);
        listRole.add(roleEmployee);
        Role roleAdmin = new Role(2, "ADMIN", null, null);
        listRole.add(roleAdmin);
        Role roleFinance = new Role(3, "FINANCE", null, null);
        listRole.add(roleFinance);
        roleRepository.saveAll(listRole);

        ArrayList<Privilege> listPrivilege = new ArrayList<>();
        listPrivilege.add(new Privilege(1, "CREATE_EMPLOYEE", null));
        listPrivilege.add(new Privilege(2, "READ_EMPLOYEE", null));
        listPrivilege.add(new Privilege(3, "UPDATE_EMPLOYEE", null));
        listPrivilege.add(new Privilege(4, "DELETE_EMPLOYEE", null));
        listPrivilege.add(new Privilege(5, "CREATE_ADMIN", null));
        listPrivilege.add(new Privilege(6, "READ_ADMIN", null));
        listPrivilege.add(new Privilege(7, "UPDATE_ADMIN", null));
        listPrivilege.add(new Privilege(8, "DELETE_ADMIN", null));
        listPrivilege.add(new Privilege(9, "CREATE_FINANCE", null));
        listPrivilege.add(new Privilege(10, "READ_FINANCE", null));
        listPrivilege.add(new Privilege(11, "UPDATE_FINANCE", null));
        listPrivilege.add(new Privilege(12, "DELETE_FINANCE", null));

        ArrayList<Category> listCategory = new ArrayList<>();
        Category categoryDigital = new Category(1, "DIGITAL", null);
        listCategory.add(categoryDigital);
        Category categoryBangunan = new Category(2, "BANGUNAN", null);
        listCategory.add(categoryBangunan);
        categoryRepo.saveAll(listCategory);

        roleRepository.saveAll(listRole);
        privilegeRepository.saveAll(listPrivilege);

        //employee
        privilegeRepository.insertRolePrivilege(1, 1);
        privilegeRepository.insertRolePrivilege(1, 2);
        privilegeRepository.insertRolePrivilege(1, 3);
        privilegeRepository.insertRolePrivilege(1, 4);
        //admin
        privilegeRepository.insertRolePrivilege(2, 5);
        privilegeRepository.insertRolePrivilege(2, 6);
        privilegeRepository.insertRolePrivilege(2, 7);
        privilegeRepository.insertRolePrivilege(2, 8);
        //finance
        privilegeRepository.insertRolePrivilege(3, 9);
        privilegeRepository.insertRolePrivilege(3, 10);
        privilegeRepository.insertRolePrivilege(3, 11);
        privilegeRepository.insertRolePrivilege(3, 12);

        ArrayList<Department> listDepartment = new ArrayList<>();
        Department departmentMarketing = new Department(1, "Marketing" , 50000000 , null);
        listDepartment.add(departmentMarketing);
        Department departmentHR = new Department(2, "Human Resource" , 75000000 , null);
        listDepartment.add(departmentHR);
        Department departmentIT = new Department(3, "Information Technology" , 100000000 , null);
        listDepartment.add(departmentIT);
        departmentRepo.saveAll(listDepartment);

        //Generate Account Admin
        if(!userRepository.findByUsername("admin").isPresent()){
            Employee employee = new Employee();
            employee.setFirstName("Admin");
            employee.setLastName("System");
            employee.setPhoneNumber("123456789");
            employee.setDepartment(departmentIT);
            User user = new User();
            user.setUsername("admin");
            String encodedPassword = bCryptPasswordEncoder
                    .encode("admin");
            user.setPassword(encodedPassword);
            user.setIsEnabled(true);
            user.setEmail("admin@yopmail.com");
            user.setEmployee(employee);
            List<Role> role = new ArrayList<>();
            role.add(roleRepository.findById(2).get());
            user.setRoles(role);
            userRepository.save(user);
        }

        ArrayList<Asset> listAsset = new ArrayList<>();
        //Asset 1
        Asset asset1 = new Asset();
        asset1.setId(1);
        asset1.setQty(10);
        asset1.setName("Laptop");
        asset1.setDescription("mengerjakan tugas, membuat materi presentasi, mengikuti kegiatan belajar mengajar secara virtual, mengakses E- Learning, mencari materi");
        asset1.setPrice(11500000);
        asset1.setImage("laptop_rog.png");
        Date submissionDate1;
        submissionDate1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/07/2011");
        asset1.setDate(submissionDate1);
        asset1.setApprovedStatus(AssetStatus.APPROVED);
        asset1.setEmployee(employeeRepo.findById(1).get());
        asset1.setCategory(categoryDigital);
        asset1.setHistories(null);
        listAsset.add(asset1);

        //Asset 2
        Asset asset2 = new Asset();
        asset2.setId(2);
        asset2.setQty(1);
        asset2.setName("Meeting Room");
        asset2.setDescription("sebagai tempat berkumpul, berdiskusi, rapat, untuk menentukan prioritas atau membuat tujuan, interview calon pekerja");
        asset2.setPrice(100000);
        asset2.setImage("meeting_room.jpg");
        Date submissionDate2;
        submissionDate2 = new SimpleDateFormat("dd/MM/yyyy").parse("21/07/2011");
        asset2.setDate(submissionDate2);
        asset2.setApprovedStatus(AssetStatus.APPROVED);
        asset2.setEmployee(employeeRepo.findById(1).get());
        asset2.setCategory(categoryBangunan);
        asset2.setHistories(null);
        listAsset.add(asset2);

        assetRepo.saveAll(listAsset);




    }
}
