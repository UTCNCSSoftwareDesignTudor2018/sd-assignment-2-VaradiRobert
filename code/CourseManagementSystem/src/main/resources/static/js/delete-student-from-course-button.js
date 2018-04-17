function deleteStudentFromCourse(studentId, courseId) {
  data = {'studentId': studentId, 'courseId': courseId};
  $.ajax({
    url: 'http://localhost:8080/teacher/courses/student/delete-student',
    method: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: data,
    success: function(response) {
      console.log(response);
    },
    error: function() {
      console.log("Error!");
    }
  });
}