<!DOCTYPE html>
<html lang="en" class="no-js">

<body>

<div class="page-container">
    <h1>Login</h1>

         <button id="bu" value="按钮"></button>
        </p>
    </div>
</div>
<script src="resource/assets/js/jquery-1.8.2.min.js"></script>

<script type="text/javascript" charset="utf-8">
    jQuery(document).ready(function () {
        $("#bu").click(function () {
            $.ajax({
                url:'/test',
                type: 'post',
                success: function (data) {
                }
            });
        });
    });
</script>

</body>
</html>





