package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
