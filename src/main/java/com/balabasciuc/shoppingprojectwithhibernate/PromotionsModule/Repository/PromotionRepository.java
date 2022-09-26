package com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Repository;

import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.Promotion;
import com.balabasciuc.shoppingprojectwithhibernate.PromotionsModule.Domain.PromotionSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("SELECT s FROM Promotion s WHERE s.promotionSeason = :promotionSeason")
    Promotion findWhatPromotionSeasonWeHave(@Param("promotionSeason") PromotionSeason promotionSeason);



    Promotion findPromotionByPromotionSeason(PromotionSeason promotionSeason);


}
