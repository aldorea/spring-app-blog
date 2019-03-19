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

import com.uv.dbcds.restcomment.assembler.CommentAssembler;
import com.uv.dbcds.restcomment.model.Comment;
import com.uv.dbcds.restcomment.service.MessagesService;

@RestController
public class CommentController {

	@Autowired
	private MessagesService messagesService;

	@Autowired
	private CommentAssembler commentAssembler;

	// Get all comments from a message
	@GetMapping("messages/{id}/comments")
	public Resources<Resource<Comment>> all(@PathVariable int id) {

		List<Resource<Comment>> comments = messagesService.getMessageComments(id).stream()
																				.map(commentAssembler::toResource)
																				.collect(Collectors.toList());
		return new Resources<>(comments, linkTo(methodOn(CommentController.class).all(id)).withSelfRel());

	}

	// Get a comment by ID
	@GetMapping("messages/{messageId}/comments/{commentId}")
	public Resource<Comment> one(@PathVariable("messageId") int messageId, @PathVariable("commentId") int commentId) {
		
		Comment comment = messagesService.getCommentById(messageId, commentId);
		return commentAssembler.toResource(comment);

	}

	// Add a new comment to a message
	@PostMapping("messages/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addComment(@PathVariable("id") int id, @RequestBody @Valid Comment newComment)
			throws URISyntaxException {

		Resource<Comment> resource = commentAssembler.toResource(messagesService.addComment(id, newComment));
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
		
	}

	// Delete a comment by ID
	@DeleteMapping("/messages/{messageId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable("messageId") int messageId,
			@PathVariable("commentId") int commentId) {
		
		messagesService.deleteComment(messageId, commentId);
		return ResponseEntity.noContent().build();
	}

	// Update a comment
	@PutMapping("/messages/{messageId}/comments/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable("messageId") int messageId,
			@PathVariable("commentId") int commentId, @RequestBody Comment newComment) throws URISyntaxException {

		Comment commentToUpdate = messagesService.updateComment(messageId, commentId, newComment);
		Resource<Comment> resource = commentAssembler.toResource(commentToUpdate);
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

}
