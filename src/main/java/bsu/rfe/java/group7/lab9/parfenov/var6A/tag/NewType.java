package bsu.rfe.java.group7.lab9.parfenov.var6A.tag;

import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.User;
import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.UserList;
import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.UserListHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


public class NewType extends SimpleTagSupport {

    private User type;

    public void setUser(User type) {
        this.type = type;
    }

    public void doTag() throws JspException, IOException {
        String errorMessage = null;
        UserList userList = (UserList) getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);

        if (type.getType()==null || type.getType().equals("")) {
            errorMessage = "Select ad type";

        } else {
            if (type.getName()==null || type.getName().equals("")) {
                errorMessage = "Username cannot be empty!";
            }
        }

        if (errorMessage==null) {
            try {
                userList.addUser(type);
                UserListHelper.saveUserList(userList);
            } catch (UserList.UserExistsException e) {}
        }

        getJspContext().setAttribute("errorMessage", errorMessage,PageContext.SESSION_SCOPE);
    }
}
