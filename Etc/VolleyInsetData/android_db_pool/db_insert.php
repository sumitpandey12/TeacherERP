<?php
$conn=mysqli_connect("localhost","root","");
mysqli_select_db($conn,"android_db");

  $name=$_POST['t1'];
  $uname=$_POST['t2'];
  $pwd=$_POST['t3'];
  
  $qry="INSERT INTO `tbl_user` (`id`, `name`, `username`, `password`) VALUES (NULL, '$name', '$uname', '$pwd')";
  mysqli_query($conn,$qry);
  
  echo "Inserted Successfully";
?>