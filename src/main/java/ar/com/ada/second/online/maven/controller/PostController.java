package ar.com.ada.second.online.maven.controller;

import ar.com.ada.second.online.maven.model.dao.JpaPostDAO;
import ar.com.ada.second.online.maven.model.dao.JpaUserDAO;
import ar.com.ada.second.online.maven.model.dao.PostDAO;
import ar.com.ada.second.online.maven.model.dao.UserDAO;
import ar.com.ada.second.online.maven.model.dto.PostDTO;
import ar.com.ada.second.online.maven.model.dto.UserDTO;
import ar.com.ada.second.online.maven.utils.Keyboard;
import ar.com.ada.second.online.maven.utils.Paginator;
import ar.com.ada.second.online.maven.view.MainView;
import ar.com.ada.second.online.maven.view.PostView;

import java.util.List;
import java.util.Optional;

public class PostController {
    private static PostController postController;
    private PostView postView = PostView.getInstance();
    private MainView mainView = MainView.getInstance();
    private JpaPostDAO jpaPostDAO = JpaPostDAO.getInstance();
    private JpaUserDAO jpaUserDAO = JpaUserDAO.getInstance();

    private PostController() {
    }

    public static PostController getInstance() {
        if (postController == null) postController = new PostController();
        return postController;
    }

    public void init() {
        boolean shouldItStay = true;
        postView.showTitlePostModule();

        while (shouldItStay) {
            Integer option = postView.postMenuSelectOption();
            switch (option) {
                case 1:
                    createPost();
                    break;
                case 2:
                    listAllPosts();
                    break;
                case 3:
                    editPost();
                    break;
                case 4:
                    deletePost();
                    break;
                case 5:
                    shouldItStay = false;
                    mainView.showTitleReturnMenu();
                    break;
                default:
                    mainView.invalidOption();
            }
        }
    }

    private void createPost() {
        postView.showTitleNewPost();
        UserDTO authorUser = getAuthorUser();

        if (authorUser != null) {
            String postBody = postView.getDataPost();

            if (postBody.equalsIgnoreCase("q")) {
                postView.cancelledProcess();
                return;
            }

            PostDTO newPost = new PostDTO(postBody, authorUser);

            PostDAO toSave = PostDAO.toDAO(newPost);

            jpaPostDAO.save(toSave);

            newPost.setId(toSave.getId());

            postView.showNewPost(newPost);

        } else {
            postView.cancelledProcess();
        }

    }

    private void listAllPosts() {
        postView.showTitleListPost();
        UserDTO authorUser = getAuthorUser();
        printRecordsPerPage(false, true, authorUser);
    }

    private void editPost() {
        postView.showTitleEditOrDeletePost(Paginator.EDIT);
        UserDTO authorUser = getAuthorUser();
        PostDAO postToEdit = getPostToEditOrDelete(Paginator.EDIT, authorUser);

        if (postToEdit != null) {
            String newPostBody = postView.getDataEditPost(postToEdit);

            if (!newPostBody.isEmpty())
                postToEdit.setBody(newPostBody);

            jpaPostDAO.save(postToEdit);

            PostDTO postDTO = PostDAO.toDTO(postToEdit);

            postView.showPost(postDTO);

        } else {
            postView.editOrDeletehPostCanceled(Paginator.EDIT);
        }
    }

    private void deletePost() {
        postView.showTitleEditOrDeletePost(Paginator.DELETE);
        UserDTO authorUser = getAuthorUser();
        PostDAO postToDelete = getPostToEditOrDelete(Paginator.DELETE, authorUser);

        if (postToDelete != null) {
            Boolean answer = postView.areYouSureToRemoveIt(postToDelete);
            if (answer) {
                Boolean hasDelete = jpaPostDAO.delete(postToDelete);

                if (hasDelete)
                    postView.postHasBeenSuccessfullyRemoved();
                else
                    postView.errorWhenDeletingRecord();

            } else {
                postView.editOrDeletehPostCanceled(Paginator.DELETE);
            }
        } else {
            postView.editOrDeletehPostCanceled(Paginator.DELETE);
        }
    }

    private PostDAO getPostToEditOrDelete(String optionEditOrDelete, UserDTO authorUser) {
        boolean shouldGetOut = false;
        Optional<PostDAO> postToEditOrDeleteOptional = Optional.empty();
        String actionInfo = Paginator.EDIT.equals(optionEditOrDelete) ? "editar" : "eliminar";
        postView.selectPostIdToEditOrDelete(actionInfo);

        Integer postIdToEditOrDelete = printRecordsPerPage(true, false, authorUser);

        if (postIdToEditOrDelete != 0) {
            while (!shouldGetOut) {
                postToEditOrDeleteOptional = jpaPostDAO.findByIdAndAuthor(postIdToEditOrDelete, authorUser);
                if (!postToEditOrDeleteOptional.isPresent()) {
                    postView.postNotExist(postIdToEditOrDelete, authorUser);
                    postIdToEditOrDelete = postView.postIdSelection(actionInfo);
                    shouldGetOut = (postIdToEditOrDelete == 0);
                } else {
                    shouldGetOut = true;
                }
            }
        }

        return postToEditOrDeleteOptional.isPresent() ? postToEditOrDeleteOptional.get() : null;
    }

    private UserDTO getAuthorUser() {
        String nickname = null;
        Boolean hasUserFound = null;
        UserDAO byNickname = null;

        while (byNickname == null) {
            if (hasUserFound != null)
                postView.userNotFound(nickname);

            nickname = postView.getNicknameToFind();

            if (nickname.equalsIgnoreCase("q"))
                break;

            byNickname = jpaUserDAO.findByNickname(nickname);
            hasUserFound = false;
        }

        return nickname.equalsIgnoreCase("q") ?
                null :
                UserDAO.toDTO(byNickname);
    }

    private Integer printRecordsPerPage(Boolean hasEditOrDelete, Boolean isHeaderShown, UserDTO authorUser) {
        int limit = 4,
                currentPage = 0,
                totalRecords,
                totalPages,
                idSelected = 0;

        List<PostDAO> posts;
        List<String> paginator;

        Integer authorUserId = (authorUser != null)
                ? authorUser.getId()
                : null;

        boolean shouldGetOut = false;

        while (!shouldGetOut) {
            totalRecords = jpaPostDAO.getTotalRecords(authorUserId);
            totalPages = (int) Math.ceil((double) totalRecords / limit);
            paginator = Paginator.buildPaginator(currentPage, totalPages);
            posts = (authorUser == null)
                    ? jpaPostDAO.findAll(currentPage * limit, limit)
                    : jpaPostDAO.findAllByUser(currentPage * limit, limit, authorUser);

            if (!posts.isEmpty()) {
                String choice = postView.printPostsPerPage(posts, paginator, isHeaderShown);

                switch (choice) {
                    case "i":
                    case "I":
                        currentPage = 0;
                        break;
                    case "a":
                    case "A":
                        if (currentPage > 0) currentPage--;
                        break;
                    case "s":
                    case "S":
                        if (currentPage + 1 < totalPages) currentPage++;
                        break;
                    case "u":
                    case "U":
                        currentPage = totalPages - 1;
                        break;
                    case "q":
                    case "Q":
                        shouldGetOut = true;
                        break;
                    default:
                        if (hasEditOrDelete) {
                            idSelected = Integer.parseInt(choice);
                            shouldGetOut = true;
                        } else {
                            if (choice.matches("^-?\\d+$")) {
                                int page = Integer.parseInt(choice);
                                if (page > 0 && page <= totalPages) currentPage = page - 1;
                            } else Keyboard.invalidData();
                        }
                }
            } else {
                shouldGetOut = true;
                postView.postsListNotFound();
            }
        }

        return idSelected;
    }
}
