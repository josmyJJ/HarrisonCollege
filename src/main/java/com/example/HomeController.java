package com.example;

import com.example.beckend.CloudinaryConfig;
import com.example.beckend.UserService;
import com.example.model.*;
import com.example.repository.ClassroomRepository;
import com.example.repository.CourseRepository;
import com.example.repository.PetRepository;
import com.example.repository.UserRepository;
import com.example.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    PetRepository petRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @RequestMapping("/")
    public String index(@ModelAttribute Pet message, Model model){
        model.addAttribute("messages", petRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/schedule")
    public String studentSchedul(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        List<Student> students = studentService.getAllStudents();
        for (Iterator<Student> s = students.iterator(); s.hasNext();) {
            Student item = s.next();
            System.out.println(item.getName());
        }
        return "studentschedule";
    }


    @RequestMapping("/student")
    public String student(Model model){
        return "student";
    }

    //classroom
    @GetMapping("/addclassroom")
    public String addclassroom(Model model){
        model.addAttribute("classroom", new Classroom());
        return "addclassroom";
    }

    @PostMapping("/processclassroom")
    public String processclassroom(@ModelAttribute("classrooms") Classroom classroom){
        classroomRepository.save(classroom);
        //return "index";
        return "classlist";
    }


    @RequestMapping("/advisor")
    public String advisor(){
        return "advisor";
    }

    @RequestMapping("/instructor")
    public String instructor(){
        return "instructor";
    }

    @RequestMapping("/course")
    public String course(){
        return "course";
    }

    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }

    @GetMapping("/add")
    public String messageForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        model.addAttribute("user_id",user.getId());
//        model.addAttribute("imageLabel", "Upload Image");
        model.addAttribute("message", new Course());
        model.addAttribute("course", new Course());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(HttpServletRequest request, @Valid @ModelAttribute Course course, BindingResult result
//                              @RequestParam("file") MultipartFile file
    ) {

//        @RequestParam("hiddenImgURL") String ImgURL
        User user = getUser();

//        if (file.isEmpty()) {
//            return "redirect:/add";
//        }
//        if (!file.isEmpty()) {
//            try {
//                Map uploadResult = cloudc.upload(file.getBytes(),
//                        ObjectUtils.asMap("resourcetype", "auto"));
//                message.setPostImg(uploadResult.get("url").toString());
//
////                user.setHash(user.getEmail());
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "redirect:/add";
//            }
////            catch (NoSuchAlgorithmException e) {
////                e.printStackTrace();
////                return "redirect:/add";
////            }
//        }
//        else {
//            if (!ImgURL.isEmpty()) {
//                message.setPostImg(ImgURL);
//            } else {
//                message.setPostImg("");
//            }
//        }

//        message.setUser(user);
//        message.setPosteddate();

//        petRepository.save(course);

        courseRepository.save(course);
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model){
        model.addAttribute("user", user);
        if(result.hasErrors()){
            return "registration";
        }else{
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "index";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", petRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@ModelAttribute Pet message, @PathVariable
            ("id") long id, Model model) {
        message = petRepository.findById(id).get();
        model.addAttribute("message", petRepository.findById(id));
        model.addAttribute("imageURL", message.getPostImg());

        if(message.getPostImg().isEmpty()) {
            model.addAttribute("imageLabel", "Upload Image");
        }
        else {
            model.addAttribute("imageLabel", "Upload New Image");
        }
        return "messageform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        petRepository.deleteById(id);
        return "redirect:/";
    }

    protected User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }
}
