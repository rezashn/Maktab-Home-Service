package repository;

import entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    boolean existsByNameAndServiceCategoryId(String name, Long serviceCategoryId);

    List<SubService> findByServiceCategoryId(Long serviceCategoryId);

}