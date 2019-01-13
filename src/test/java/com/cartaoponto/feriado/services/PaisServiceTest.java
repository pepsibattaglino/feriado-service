package com.cartaoponto.feriado.services;

import com.cartaoponto.feriado.dto.PaisDTO;
import com.cartaoponto.feriado.entities.PaisEntity;
import com.cartaoponto.feriado.exceptions.InvalidInputDataException;
import com.cartaoponto.feriado.exceptions.NotFoundException;
import com.cartaoponto.feriado.repositories.PaisRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(PaisService.class)
public class PaisServiceTest {

    @Autowired
    private PaisService paisService;
    @MockBean
    private PaisRepository paisRepository;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testUpdatePaisSuccess() {
        PaisEntity paisSalvoNoBanco = new PaisEntity(1L, "Brasil");
        mockFindPais(paisSalvoNoBanco);
        mockSavePais();

        PaisDTO paisAlterado = new PaisDTO(1L, "Argentina");

        paisService.salvarPais(paisAlterado);
        PaisEntity paisSalvoAposAlteracao = captureSavePais();

        Assert.assertEquals("Argentina", paisSalvoAposAlteracao.getDescricao());
    }

    private void mockFindPais(PaisEntity paisEntity) {
        when(paisRepository.findOne(anyLong())).thenReturn(paisEntity);
    }

    private void mockSavePais() {
        when(paisRepository.save(any(PaisEntity.class))).thenAnswer(
                new Answer<PaisEntity>() {
                    @Override
                    public PaisEntity answer(InvocationOnMock invocation) throws Throwable {
                        Object[] args = invocation.getArguments();
                        return (PaisEntity) args[0];
                    }
                }
        );
    }

    private PaisEntity captureSavePais() {
        final ArgumentCaptor<PaisEntity> captor = ArgumentCaptor.forClass(PaisEntity.class);
        verify(paisRepository).save(captor.capture());
        return captor.getValue();
    }

    @Test
    public void testUpdateNonExistentPais() {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(org.hamcrest.Matchers.equalTo("Não existe um país salvo com o código informado."));
        mockFindPais(null);
        PaisDTO paisASerSalvo = new PaisDTO(1L, "Argentina");

        paisService.salvarPais(paisASerSalvo);
    }

    @Test
    public void testDeletePaisSuccess() {
        PaisDTO paisASerRemovido = new PaisDTO(1L, "Brasil");
        PaisEntity paisSalvoNoBanco = new PaisEntity(1L, "Brasil");
        mockFindPais(paisSalvoNoBanco);
        paisService.removerPais(paisASerRemovido);
    }

    @Test
    public void testDeleteNullPais() {
        expectedException.expect(InvalidInputDataException.class);
        expectedException.expectMessage(org.hamcrest.Matchers.equalTo("Não é possível remover um país com ID nulo."));

        PaisDTO paisASerRemovido = new PaisDTO(null, "Brasil");

        paisService.removerPais(paisASerRemovido);
    }

    @Test
    public void testDeleteNonExistentPais() {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(org.hamcrest.Matchers.equalTo("Não existe um país salvo com o código informado."));
        mockFindPais(null);

        PaisDTO paisASerRemovido = new PaisDTO(1L, "Argentina");
        paisService.removerPais(paisASerRemovido);
    }

    @Test
    public void testFindPaisSuccess() {
        PaisEntity paisSalvoNoBanco = new PaisEntity(1L, "Argentina");
        mockFindPais(paisSalvoNoBanco);

        PaisDTO paisASerConsultado = new PaisDTO(1L, "Argentina");
        paisASerConsultado = paisService.consultarPais(paisASerConsultado);

        Assert.assertEquals(paisSalvoNoBanco.getId(), paisASerConsultado.getId());
        Assert.assertEquals(paisSalvoNoBanco.getDescricao(), paisASerConsultado.getDescricao());
    }

    @Test(expected = NotFoundException.class)
    public void testFindNonExistentPais() {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage(org.hamcrest.Matchers.equalTo("Não existe um país salvo com o código informado."));
        mockFindPais(null);

        PaisDTO paisASerConsultado = new PaisDTO(1L, "Argentina");
        paisASerConsultado = paisService.consultarPais(paisASerConsultado);
    }
}
