function cancelEnrollmentRequest(studentId, courseId) {
  data = {'studentId': studentId, 'courseId': courseId};
  console.log(data);
  $.post('http://localhost:8080/student/enrollments/cancel-enrollment-request', data);
}
