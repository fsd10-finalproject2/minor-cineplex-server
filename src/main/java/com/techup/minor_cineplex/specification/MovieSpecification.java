package com.techup.minor_cineplex.specification;

import com.techup.minor_cineplex.entity.Movie;
import com.techup.minor_cineplex.entity.Showtime;
import com.techup.minor_cineplex.dto.request.movie.MovieSearchCriteria;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {

    public static Specification<Movie> build(MovieSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(criteria.getName())) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(criteria.getLanguage())) {
                predicates.add(cb.equal(cb.lower(root.get("language")), criteria.getLanguage().toLowerCase()));
            }

            if (StringUtils.hasText(criteria.getGenre())) {
                predicates.add(cb.isMember(criteria.getGenre(), root.get("genres")));
            }

            if (criteria.getReleaseDate() != null) {
                predicates.add(cb.equal(root.get("releaseDate"), criteria.getReleaseDate()));
            }

            if (StringUtils.hasText(criteria.getCity())) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Showtime> showtimeRoot = subquery.from(Showtime.class);
                subquery.select(showtimeRoot.get("movie").get("id"))
                        .where(cb.equal(cb.lower(showtimeRoot.get("cinema").get("city")), criteria.getCity().toLowerCase()));
                predicates.add(cb.in(root.get("id")).value(subquery));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
