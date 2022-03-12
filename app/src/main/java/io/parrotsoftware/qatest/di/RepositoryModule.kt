package io.parrotsoftware.qatest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.parrotsoftware.qatest.network.service.AuthService
import io.parrotsoftware.qatest.network.service.ProductService
import io.parrotsoftware.qatest.persistence.AuthDao
import io.parrotsoftware.qatest.persistence.ProductDao
import io.parrotsoftware.qatest.repository.AuthRepository
import io.parrotsoftware.qatest.repository.MainRepository
import io.parrotsoftware.qatest.repository.ProductRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        authDao: AuthDao
    ): MainRepository {
        return MainRepository(authDao)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        authService: AuthService,
        authDao: AuthDao
    ): AuthRepository {
        return AuthRepository(authService, authDao)
    }

    @Provides
    @ViewModelScoped
    fun provideProductRepository(
        productService: ProductService,
        authDao: AuthDao,
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepository(productService, authDao, productDao)
    }
}