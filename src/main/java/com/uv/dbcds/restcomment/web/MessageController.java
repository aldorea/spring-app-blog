package com.uv.dbcds.restcomment.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uv.dbcds.restcomment.assembler.MessageAssembler;
import com.uv.dbcds.restcomment.model.Message;
import com.uv.dbcds.restcomment.service.MessagesService;

@RestController
public class MessageController {

	// MessageAssembler messageAssembler;

	@Autowired
	private MessagesService messagesService;

	@Autowired
	private MessageAssembler messageAssembler;

//	 MessageController(MessageAssembler messageAssembler){
//	        this.messageAssembler = messageAssembler;
//	    }

	// Get all messages
	@GetMapping("/messages")
	public Resources<Resource<Message>> all() {

		List<Resource<Message>> messages = messagesService.getMessages().stream().map(messageAssembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(messages, linkTo(methodOn(MessageController.class).all()).withSelfRel());
	}

	// Get message by ID
	@GetMapping("messages/{id}")
	public Resource<Message> one(@PathVariable int id) {

		Message message = messagesService.getMessageById(id);
		return messageAssembler.toResource(message);

	}

	// Add a new message
	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<?> newMessage(@RequestBody @Valid Message newMessage) throws URISyntaxException {

		Resource<Message> resource = messageAssembler.toResource(messagesService.addMessage(newMessage));

		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@DeleteMapping("/messages/{id}")
	public ResponseEntity<?> deleteMessage(@PathVariable("id") int idMessage) {
		messagesService.deleteMessage(idMessage);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/messages/{id}")
	public ResponseEntity<?> updateMessage(@PathVariable("id") int id, @RequestBody Message newMessage)
			throws URISyntaxException {

		Message message = messagesService.updateMessage(id, newMessage);
		Resource<Message> resource = messageAssembler.toResource(message);
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);

	}

	// Like a comment
	@PutMapping("/messages/{id}/like")
	public ResponseEntity<?> likeMessage(@PathVariable int id) throws URISyntaxException {
		
		Message message = messagesService.likeMessage(id);
		Resource<Message> resource = messageAssembler.toResource(message);
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

}