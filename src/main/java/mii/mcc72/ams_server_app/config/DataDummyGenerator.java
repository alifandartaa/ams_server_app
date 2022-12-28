package mii.mcc72.ams_server_app.config;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Category;
import mii.mcc72.ams_server_app.models.Privilege;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.repos.CategoryRepo;
import mii.mcc72.ams_server_app.repos.PrivilegeRepo;
import mii.mcc72.ams_server_app.repos.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DataDummyGenerator implements CommandLineRunner {
    private final PrivilegeRepo privilegeRepository;
    private final RoleRepo roleRepository;
    private final CategoryRepo categoryRepo;


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
    }
}
