package com.test.gymapptest
import app.cash.turbine.test
import com.test.gymapptest.data.repository.EjercicioRepository
import com.test.gymapptest.domain.UseCase.ejercicio.GetEjerciciosUseCase
import com.test.gymapptest.ui.model.EjercicioM
import com.test.gymapptest.ui.model.TipoM
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest // Importante para probar coroutines
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetEjerciciosUseCaseTest {
    private lateinit var mockEjercioRepository: EjercicioRepository
    private lateinit var getEjerciciosUseCase: GetEjerciciosUseCase

    private val tipoEjemplo = TipoM(id = 1, nombre = "Pecho")
    private val ejercicio1 = EjercicioM(id = 1, nombre = "Press Banca", tipo = tipoEjemplo)
    private val ejercicio2 = EjercicioM(id = 2, nombre = "Flexiones", tipo = tipoEjemplo)
    private val listaEjerciciosEjemplo = listOf(ejercicio1, ejercicio2)

    @Before
    fun setUp() {
        mockEjercioRepository = mock()
        getEjerciciosUseCase = GetEjerciciosUseCase(mockEjercioRepository)
    }


    @Test
    fun `invoke cuando el repositorio devielve ejercicios entonces retorna flow con lista de ejercicios`() = runTest {

           whenever(mockEjercioRepository.getEjercicios).thenReturn(flowOf(listaEjerciciosEjemplo))

           getEjerciciosUseCase().test {
               val itemsEmitidos = awaitItem()
               assertEquals(listaEjerciciosEjemplo, itemsEmitidos)
               awaitComplete()
           }

    }

    @Test
    fun `invoke CUANDO repositorio devuelve ejercicios ENTONCES retorna Flow con lista vacia`() = runTest{
        //Arrange

        val listaVacia = emptyList<EjercicioM>()
        whenever(mockEjercioRepository.getEjercicios).thenReturn(flowOf(listaVacia))

        //Act & Assert
        getEjerciciosUseCase().test{
            val itemsEmitidos = awaitItem()
            assertEquals(listaVacia, itemsEmitidos)
            awaitComplete()
        }
    }
    @Test
    fun `invoke CUANDO repositorio emite error ENTONCES retorna Flow con ese error`() = runTest {
        // Arrange

        val errorEsperado = Exception("Error de prueba")
        whenever(mockEjercioRepository.getEjercicios).thenReturn(flow { throw errorEsperado })

        // Act & Assert
        getEjerciciosUseCase().test {
            awaitError()
            assertEquals(errorEsperado, errorEsperado)
        }
    }
}