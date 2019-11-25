package hello;

import javax.validation.Valid;

import hello.storage.CustomFileValidator;
import hello.storage.StorageFileNotFoundException;
import hello.storage.StorageService;
import hello.storage.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;


@Controller
public class WebController implements WebMvcConfigurer {

    private final StorageService storageService;
    private final UserRepository userRepository;

    @Autowired
    CustomFileValidator customFileValidator;

    @Autowired
    public WebController(StorageService storageService, UserRepository userRepository) {
        this.storageService = storageService;
        this.userRepository = userRepository;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(User user) {
        return "form";
    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }
        userRepository.insert(user);

        return "redirect:/results";
    }
    @GetMapping("/results")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(WebController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/results")
    public String handleFileUpload( @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        // file handling to upload it in the server path
        //MultipartFile file = fileUploadModel.getFile();
        customFileValidator.validate(file, bindingResult);
        if (bindingResult.hasErrors()) {
            return "uploadForm";
        }
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
