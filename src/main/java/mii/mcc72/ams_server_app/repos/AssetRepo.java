package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Integer> {

    boolean existsAssetByName(String name);
    Optional<Asset> findByName(String name);

}

