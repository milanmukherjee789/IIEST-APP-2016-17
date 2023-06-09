<?php
/**
 * @package   T3 Blank
 * @copyright Copyright (C) 2005 - 2012 Open Source Matters, Inc. All rights reserved.
 * @license   GNU General Public License version 2 or later; see LICENSE.txt
 */

defined('_JEXEC') or die;
?>

<!-- BACK TOP TOP BUTTON -->
<div id="back-to-top" data-spy="affix" data-offset-top="300" class="back-to-top hidden-xs hidden-sm affix-top">
  <button class="btn btn-primary" title="Back to Top"><i class="fa fa-caret-up"></i></button>
</div>
<script type="text/javascript">
(function($) {
	// Back to top
	$('#back-to-top').on('click', function(){
		$("html, body").animate({scrollTop: 0}, 500);
		return false;
	});
    $(window).load(function(){
        // hide button to top if the document height not greater than window height*2;using window load for more accurate calculate.    
        if ((parseInt($(window).height())*2)>(parseInt($(document).height()))) {
            $('#back-to-top').hide();
        } 
    });
})(jQuery);
</script>
<!-- BACK TO TOP BUTTON -->

<!-- FOOTER -->
<footer id="t3-footer" class="wrap t3-footer">
	<?php if ($this->checkSpotlight('footer-sl', 'footer-1, footer-2, footer-3, footer-4, footer-5, footer-6')) : ?>
		<!-- FOOTER SPOTLIGHT -->
		<div class="container hidden-xs">
			<?php $this->spotlight('footer-sl', 'footer-1, footer-2, footer-3, footer-4, footer-5, footer-6') ?>
		</div>
		<!-- //FOOTER SPOTLIGHT -->
	<?php endif ?>
	<?php
	echo "<div class='container-fluid custom-copyright text-center'>
    <p>&copy; Indian Institute of Engineering Science and Technology, 2016</p>
  </div>"
?>
</footer>
<!-- //FOOTER -->
 <!--Indian Institute of Engineering Science and Technology, 2016