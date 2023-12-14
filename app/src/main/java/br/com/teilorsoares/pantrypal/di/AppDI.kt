package br.com.teilorsoares.pantrypal.di

import androidx.room.Room
import br.com.teilorsoares.pantrypal.data.database.AppDatabase
import br.com.teilorsoares.pantrypal.data.repository.FoodRepositoryImpl
import br.com.teilorsoares.pantrypal.data.repository.LocationRepositoryImpl
import br.com.teilorsoares.pantrypal.data.repository.ShoppingItemRepositoryImpl
import br.com.teilorsoares.pantrypal.domain.repository.FoodRepository
import br.com.teilorsoares.pantrypal.domain.repository.LocationRepository
import br.com.teilorsoares.pantrypal.domain.repository.ShoppingItemRepository
import br.com.teilorsoares.pantrypal.domain.usecase.food.DeleteFoodUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByDeleteUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByExpireUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByLocationUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.GetFoodSortedByMealUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.InsertFoodUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.UpdateFoodDiscardUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.food.UpdateFoodQuantityUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.location.GetLocationsUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.location.InsertLocationUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.DeleteShoppingItemUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.GetShoppingItemUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.InsertShoppingItemUseCase
import br.com.teilorsoares.pantrypal.domain.usecase.shippingitem.UpdateShoppingItemPurchasedUseCase
import br.com.teilorsoares.pantrypal.presentation.screens.main.MainViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addfood.AddFoodViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addlocation.AddLocationViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.addshoppingitem.AddShoppingItemViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.home.HomeViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.location.LocationViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.meal.MealViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.main.routes.shoppinglist.ShoppingListViewModel
import br.com.teilorsoares.pantrypal.presentation.screens.test.deleteditems.DeletedItemsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.dsl.module

object AppDI {
    private val viewModelModule = module {
        viewModel { MainViewModel() }

        viewModel {
            AddFoodViewModel(
                insertFoodUseCase = get(),
                getLocationsUseCase = get()
            )
        }

        viewModel { AddLocationViewModel(insertLocationUseCase = get()) }

        viewModel {
            HomeViewModel(
                getFoodSortedByExpireUseCase = get(),
                updateFoodDiscardUseCase = get(),
                insertShoppingItemUseCase = get(),
                deleteFoodUseCase = get(),
                updateFoodQuantityUseCase = get()
            )
        }

        viewModel {
            LocationViewModel(
                getFoodSortedByLocationUseCase = get(),
                updateFoodDiscardUseCase = get(),
                deleteFoodUseCase = get(),
                insertShoppingItemUseCase = get(),
                updateFoodQuantityUseCase = get()
            )
        }

        viewModel {
            MealViewModel(
                getFoodSortedByMealUseCase = get(),
                updateFoodDiscardUseCase = get(),
                deleteFoodUseCase = get(),
                insertShoppingItemUseCase = get(),
                updateFoodQuantityUseCase = get()
            )
        }

        viewModel {
            AddShoppingItemViewModel(
                insertShoppingItemUseCase = get()
            )
        }

        viewModel {
            ShoppingListViewModel(
                getShoppingItemUseCase = get(),
                updateShoppingItemPurchasedUseCase = get(),
                deleteShoppingItemUseCase = get()
            )
        }

        viewModel {
            DeletedItemsViewModel(
                getFoodSortedByDeleteUseCase = get()
            )
        }
    }

    private val useCaseModule = module {
        // Food Use Cases
        factory { GetFoodSortedByExpireUseCase(get<FoodRepository>()::getFoodByExpire) }
        factory { GetFoodSortedByDeleteUseCase(get<FoodRepository>()::getFoodByDelete) }
        factory { GetFoodSortedByLocationUseCase(get<FoodRepository>()::getFoodByLocation) }
        factory { GetFoodSortedByMealUseCase(get<FoodRepository>()::getFoodByMeal) }
        factory { InsertFoodUseCase(get<FoodRepository>()::insertFood) }
        factory { DeleteFoodUseCase(get<FoodRepository>()::deleteFood) }
        factory { UpdateFoodDiscardUseCase(get<FoodRepository>()::updateFoodConsumedOrDiscarded) }
        factory { UpdateFoodQuantityUseCase(get<FoodRepository>()::updateFoodQuantity) }

        // Location Use Cases
        factory { GetLocationsUseCase(get<LocationRepository>()::getLocations) }
        factory { InsertLocationUseCase(get<LocationRepository>()::insertLocation) }

        // Shopping Item Use Cases
        factory { GetShoppingItemUseCase(get<ShoppingItemRepository>()::getShoppingItems) }
        factory { InsertShoppingItemUseCase(get<ShoppingItemRepository>()::insertShoppingItem) }
        factory { UpdateShoppingItemPurchasedUseCase(get<ShoppingItemRepository>()::updateShoppingItemPurchased) }
        factory { DeleteShoppingItemUseCase(get<ShoppingItemRepository>()::deleteShoppingItem) }
    }

    private val repositoryModule = module {
        factory<FoodRepository> {
            val database = get<AppDatabase>()

            FoodRepositoryImpl(
                appDatabase = database,
                foodDao = database.foodDao(),
                locationDao = database.locationDao()
            )
        }

        factory<LocationRepository> {
            LocationRepositoryImpl(locationDao = get<AppDatabase>().locationDao())
        }

        factory<ShoppingItemRepository> {
            ShoppingItemRepositoryImpl(shoppingItemDao = get<AppDatabase>().shoppingItemDao())
        }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                context = get(),
                klass = AppDatabase::class.java,
                name = "pantrypal_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    fun provideModules(): List<Module> = listOf(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        databaseModule
    )
}

class AppComponent : KoinComponent {
    val getFoodSortedByExpireUseCase: GetFoodSortedByExpireUseCase by inject()
}