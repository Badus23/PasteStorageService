package com.mamchura.pasteService;

import com.mamchura.pasteService.api.PasteStatus;
import com.mamchura.pasteService.exceptions.HashNotFoundException;
import com.mamchura.pasteService.models.TextEntity;
import com.mamchura.pasteService.repositories.TextEntityRepository;
import com.mamchura.pasteService.services.TextEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteServiceTest {
    @Autowired
    private TextEntityService textEntityService;

    @MockBean
    private TextEntityRepository textEntityRepository;

    @Test
    public void notExistHash(){
        when(textEntityRepository.findByHash(anyInt())).thenThrow(HashNotFoundException.class);
        assertThrows(HashNotFoundException.class, () -> textEntityService.findOneByHash(2115123313));
    }

    @Test
    public void getExistHash(){
        TextEntity expected = new TextEntity();
        expected.setHash(1);
        expected.setData("11");
        expected.setPublicStatus(PasteStatus.PUBLIC);

        when(textEntityRepository.findByHash(1)).thenReturn(expected);

        TextEntity actual = textEntityRepository.findByHash(1);

        assertEquals(expected, actual);
    }

}
