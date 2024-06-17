package com.watermelon.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.watermelon.dto.response.PageResponse;
import com.watermelon.entity.Book;
import com.watermelon.enums.ELanguage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class SearchRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public PageResponse<List<Book>> findAllBooksByCustomizeQuery(
			String title,
			String categoryName,
			String authorName,
			String publisherName,
			String language,
			int pageNo,
			int pageSize,
			Sort sort
			) {
				StringBuilder where = new StringBuilder(" WHERE 1=1");
				List<Long> bookIdsWithAuthor = new LinkedList<>();
				if (StringUtils.hasLength(title)) {
					where.append(" AND (b.title LIKE :title OR b.description LIKE :description)");
				}
				if (StringUtils.hasLength(categoryName)) {
					where.append(" AND b.category.name= :categoryName");
				}
				if (StringUtils.hasLength(authorName)) {
					TypedQuery<Book> subQuery = entityManager.createQuery(
							"SELECT b FROM Book b JOIN b.listAuthors a WHERE a.name = :authorName", Book.class);
					subQuery.setParameter("authorName", authorName);
					List<Book> booksWithAuthor = subQuery.getResultList();
					bookIdsWithAuthor = booksWithAuthor.stream().map(Book::getId).toList();
					where.append(" AND b.id IN :bookIdsWithAuthor");
				}
				if (StringUtils.hasLength(publisherName)) {
					where.append(" AND b.publisher.name= :publisherName");
				}
				if (StringUtils.hasLength(language)) {
					where.append(" AND b.language= :language");
				}
				StringBuilder orderByClause = new StringBuilder();
				if (sort.isSorted()) {
					orderByClause.append(" ORDER BY ");
					for (Sort.Order order : sort) {
						orderByClause.append("b.").append(order.getProperty()).append(" ").append(order.getDirection())
								.append(", ");
					}
					orderByClause.setLength(orderByClause.length() - 2);
				}

				TypedQuery<Book> selectQuery = entityManager
						.createQuery(String.format("SELECT b FROM Book b %s %s", where, orderByClause), Book.class);

				if (StringUtils.hasLength(title)) {
					selectQuery.setParameter("title", String.format("%%%s%%", title));
					selectQuery.setParameter("description", String.format("%%%s%%", title));
				}
				if (StringUtils.hasLength(categoryName)) {
					selectQuery.setParameter("categoryName", categoryName);
				}
				if (StringUtils.hasLength(authorName)) {
					selectQuery.setParameter("bookIdsWithAuthor", bookIdsWithAuthor);
				}
				if (StringUtils.hasLength(publisherName)) {
					selectQuery.setParameter("publisherName", publisherName);
				}
				if (StringUtils.hasLength(language)) {
					selectQuery.setParameter("language", ELanguage.valueOf(language.toUpperCase()));
				}
				Long totalElements = (long) selectQuery.getResultList().size();

				selectQuery.setFirstResult(pageNo);
				selectQuery.setMaxResults(pageSize);

				List<Book> bookList = selectQuery.getResultList();
				int totalPage = 0;
				if (totalElements != 0)
					totalPage = totalElements < pageSize ? 1 : (int) (totalElements / pageSize);
				return new PageResponse<>(pageNo, pageSize, totalPage, totalElements, bookList);
			}
}
