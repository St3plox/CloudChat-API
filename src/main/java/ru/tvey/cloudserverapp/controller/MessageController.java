package ru.tvey.cloudserverapp.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tvey.cloudserverapp.entity.messaging.Message;
import ru.tvey.cloudserverapp.service.message.MessageService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;

@RestController
@RequestMapping("/cloud/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @PostMapping
    public ResponseEntity<HttpStatus> sendMessage(Authentication auth,
                                                  @RequestParam @NotBlank String text,
                                                  @RequestParam LocalDate date,
                                                  @RequestParam String groupId,
                                                  @RequestParam(required = false) String fileId) throws Exception {

        Long idOfGroup = Long.valueOf(groupId);
        Long idOfFile = null;
        if (fileId != null) {
            idOfFile = Long.valueOf(fileId);
        }
        messageService.sendMessage(auth, text, date, idOfGroup, idOfFile);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id, Authentication auth)
            throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        return new ResponseEntity<>(messageService.getMessage(auth, id), HttpStatus.ACCEPTED);
    }

    @GetMapping("group/{id}/{n}")
    public ResponseEntity<Message[]> getLastNMessages (@PathVariable long id, Authentication auth, @PathVariable int n)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException,
            InvalidKeySpecException, InvalidKeyException {

        Message[] messages = messageService.getLastNMessages(auth, id, n);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable long id, Authentication auth) {
        messageService.deleteMessage(auth, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
