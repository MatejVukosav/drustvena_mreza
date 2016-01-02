package com.example.vuki.drustvena_mreza_faks.mock;

import com.example.vuki.drustvena_mreza_faks.models.Comment;
import com.example.vuki.drustvena_mreza_faks.models.Post;
import com.example.vuki.drustvena_mreza_faks.models.Posts;
import com.example.vuki.drustvena_mreza_faks.models.User;
import com.example.vuki.drustvena_mreza_faks.models.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vuki on 6.11.2015..
 */
public class MockUsers {


    public static Users getUsers(int numOfUsers) {
        List<User> usersList = new ArrayList<>();

        for (int i = 0; i < numOfUsers; i++) {
            usersList.add(getUser());
        }

        Users users = new Users();
        users.setUsers(usersList);
        return users;
    }

    public static List<Comment> getComments(int numOfComments) {
        List<Comment> commentList = new ArrayList<>();

        for (int i = 0; i < numOfComments; i++) {
            commentList.add(geComment());
        }
        return commentList;
    }
    public static Comment geComment() {
        Comment comment = new Comment();
        comment.setUsername("vukicaa");
        comment.setMessage("ovaj post je baas suuuper!!");
        comment.setFirstName("vukiFirst");
        comment.setLastName("vukiLast");

        return comment;
    }


    public static User getUser() {
        User user = new User();
        user.setUsername("vukicaa");
        user.setEmail("vuki146@gmail.com");
        user.setFirstName("vukiFirst");
        user.setLastName("vukiLast");

        return user;
    }

    public static Posts getFivePosts(int numOfPosts) {
        List<Post> postList = new ArrayList<>();

        for (int i = 0; i < numOfPosts; i++) {
            Post post = getPost();
            if (i % 2 == 0) {
              //  post.setPostImage(null);
            }
            postList.add(post);
        }

        Posts posts = new Posts();
        posts.setPosts(postList);
        return posts;
    }

    public static Post getPost() {
        Post post = new Post();
        /*post.setMessage("eine meine deine kleine poruka");
        post.setPostImage("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTbeE9A24BAVONXhxBftCIJxjb40SOBf3_qhel-3-ShD-RGajGyX_HAhYuG");
        post.setCreator(getUser());
        post.setCreatedAt("21.05.2012");
        post.setLikes_num(140);
        post.setCommentNum(3);*/

        return post;
    }

}
