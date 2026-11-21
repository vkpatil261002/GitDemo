package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import com.google.gson.Gson;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO dao = new StudentDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        List<Student> list = dao.getAllStudents();
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student s = new Student();
        s.setName(req.getParameter("name"));
        s.setAge(Integer.parseInt(req.getParameter("age")));
        s.setCourse(req.getParameter("course"));

        boolean saved = dao.addStudent(s);
        resp.setContentType("application/json");
        resp.getWriter().print(saved ? "{\"message\":\"Added\"}" : "{\"error\":\"Failed\"}");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Student s = new Student();
        s.setId(Integer.parseInt(req.getParameter("id")));
        s.setName(req.getParameter("name"));
        s.setAge(Integer.parseInt(req.getParameter("age")));
        s.setCourse(req.getParameter("course"));

        boolean updated = dao.updateStudent(s);
        resp.setContentType("application/json");
        resp.getWriter().print(updated ? "{\"message\":\"Updated\"}" : "{\"error\":\"Failed\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean deleted = dao.deleteStudent(id);
        resp.setContentType("application/json");
        resp.getWriter().print(deleted ? "{\"message\":\"Deleted\"}" : "{\"error\":\"Failed\"}");
    }
}
