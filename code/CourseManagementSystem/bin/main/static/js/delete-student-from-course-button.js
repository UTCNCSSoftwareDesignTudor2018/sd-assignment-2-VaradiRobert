function deleteStudentFromCourse(studentId, courseId) {
  data = {'studentId': studentId, 'courseId': courseId};
  console.log(data);
  $.post(
    'http://localhost:8080/teacher/courses/student/delete-student',
    data);
}
