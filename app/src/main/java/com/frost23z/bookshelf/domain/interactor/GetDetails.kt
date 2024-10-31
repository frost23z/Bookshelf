package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.domain.repository.ContributorsRepository

class GetDetails(
    private val contributorsRepository: ContributorsRepository
) {
    suspend fun getContributorsByBookId(bookId: Long) = contributorsRepository.getContributorsByBookId(bookId)
}