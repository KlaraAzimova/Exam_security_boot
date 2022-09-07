package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.entity.Lesson;
import peaksoft.entity.Video;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.VideoRepository;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;

    public VideoService(VideoRepository videoRepository, LessonRepository lessonRepository) {
        this.videoRepository = videoRepository;
        this.lessonRepository = lessonRepository;
    }

    public void addVideo(Long id, Video video) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.setVideo(video);
        video.setLesson(lesson);
        videoRepository.save(video);
    }

    public Video getById(Long id) {
        return videoRepository.findById(id).get();
    }

    public List<Video> getVideoByLessonId(Long id) {
        return videoRepository.findVideosByLesson_LessonId(id);
    }

    public void updateVideo(Long id, Video video) {
        Video video1 = videoRepository.findById(id).get();
        video1.setVideoName(video.getVideoName());
        video1.setVideoId(video.getVideoId());
        video1.setLink(video.getLink());
        videoRepository.save(video1);
    }


    public void deleteVideo(Long id) {
        Video video = videoRepository.findById(id).get();
        video.setLesson(null);
        videoRepository.save(video);
        videoRepository.deleteById(id);
    }
}
