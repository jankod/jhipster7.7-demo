package hr.ja.repository;

import hr.ja.domain.Project;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Project entity.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query("select project from Project project where project.owner.login = ?#{principal.username}")
    List<Project> findByOwnerIsCurrentUser();

    default Optional<Project> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Project> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Project> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct project from Project project left join fetch project.owner",
        countQuery = "select count(distinct project) from Project project"
    )
    Page<Project> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct project from Project project left join fetch project.owner")
    List<Project> findAllWithToOneRelationships();

    @Query("select project from Project project left join fetch project.owner where project.id =:id")
    Optional<Project> findOneWithToOneRelationships(@Param("id") Long id);
}
