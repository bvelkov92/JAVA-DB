package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.config.constants.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Override
    Optional<Offer> findById(Long aLong);

    List<Offer>
    findAllByApartments_ApartmentTypeOrderByApartments_AreaDescPriceAsc(ApartmentType apartmentType);

}
