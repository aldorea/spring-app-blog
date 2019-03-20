package com.uv.dbcds.restcomment.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.uv.dbcds.restcomment.model.Comment;
import com.uv.dbcds.restcomment.web.CommentController;


@Component
public class CommentAssembler  implements  ResourceAssembler<Comment, Resource<Comment>> {

	@Override
	public Resource<Comment> toResource(Comment comment) {
		return new Resource<>(comment,
			linkTo(methodOn(CommentController.class).getComment(comment.getParent().getId(), comment.getId())).withSelfRel(),
			linkTo(methodOn(CommentController.class).getComments(comment.getParent().getId())).withSelfRel());
	}
	
}
