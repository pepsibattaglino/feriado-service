package com.cartaoponto.feriado.services;

import com.cartaoponto.feriado.exceptions.InvalidInputDataException;
import com.cartaoponto.feriado.exceptions.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(PaisService.class)
public class PaisServiceTest {

    @Test
    public void testUpdatePaisSuccess() {

    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNonExistentPais() {

    }

    @Test
    public void testDeletePaisSuccess() {

    }

    @Test(expected = InvalidInputDataException.class)
    public void testDeleteNullPais() {

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNonExistentPais() {

    }

    @Test
    public void testFindPaisSuccess() {

    }

    @Test(expected = NotFoundException.class)
    public void testFindNonExistentPais() {

    }
}
