package com.uv.dbcds.restcomment.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.uv.dbcds.restcomment.model.Message;
import com.uv.dbcds.restcomment.web.MessageController;


@Component
public class MessageAssembler implements  ResourceAssembler<Message, Resource<Message>> {

	@Override
	public Resource<Message> toResource(Message message) {
		return new Resource<>(message,
			linkTo(methodOn(MessageController.class).one(message.getId())).withSelfRel(),
			linkTo(methodOn(MessageController.class).all()).withRel("messages"));
	}

}
