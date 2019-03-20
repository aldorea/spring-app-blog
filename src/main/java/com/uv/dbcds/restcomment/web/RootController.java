package com.uv.dbcds.restcomment.web;

import java.util.concurrent.LinkedTransferQueue;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RootController {
	@GetMapping
	public ResourceSupport index() {
		ResourceSupport rootResource = new ResourceSupport();
//		rootResource.add(linkTo(methodOn(MessageController.class).getMessages()).withRel("messages"));
//		rootResource.add(linkTo(methodOn(CommentController.class).getComments()).withRel("comments"));
	
		return rootResource;
	}
}
