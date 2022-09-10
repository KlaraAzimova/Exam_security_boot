package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Video;
import peaksoft.service.VideoService;

@Controller
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/all/{lessonId}")
    private String getAllVideos(@PathVariable("lessonId") Long lessonId, Model model) {
        model.addAttribute("allVideos", videoService.getVideoByLessonId(lessonId));
        model.addAttribute("lesson", lessonId);
        return "video/mainVideo";
    }

    @GetMapping("{lessonId}/new")
    private String newVideo(@PathVariable("lessonId") Long id, Model model) {
        model.addAttribute("newVideo", new Video());
        model.addAttribute("lessonId", id);
        return "video/newVideo";
    }

    @PostMapping("{lessonId}/save")
    private String saveVideo(@PathVariable("lessonId") Long id, @ModelAttribute("newVideo") Video video) {
        videoService.addVideo(id, video);
        return "redirect:/videos/all/ " + id;
    }

    @GetMapping("/find/{videoId}")
    private String getVideoById(@PathVariable("videoId") Long id, Model model) {
        model.addAttribute("video", videoService.getById(id));
        return "video/mainVideo";
    }

    @GetMapping("/update/{videoId}")
    private String updateVideo(@PathVariable("videoId") Long id, Model model) {
        Video video = videoService.getById(id);
        model.addAttribute("video", video);
        model.addAttribute("lessonId", video.getLesson().getLessonId());
        return "video/updateVideo";
    }

    @PostMapping("/{lessonId}/{videoId}/update")
    private String saveUpdateVideo(@PathVariable("lessonId") Long id,
                                   @PathVariable("videoId") Long videoId,
                                   @ModelAttribute("video") Video video) {
        videoService.updateVideo(videoId, video);
        return "redirect:/videos/all/ " + id;
    }

    @RequestMapping("/{lessonId}/{videoId}/delete")
    private String deleteVideo(@PathVariable("lessonId") Long id, @PathVariable("videoId") Long videoId) {
        videoService.deleteVideo(videoId);
        return "redirect:/videos/all/ " + id;
    }
}
