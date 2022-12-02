window.onscroll = function () {scrollFunction();};

function scrollFunction() {
    if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {

        document.getElementById("navbar").style.transition = "all ease 0.5s"
        document.getElementById("navbar").style.background = "#212529";
    } else {
        document.getElementById("navbar").style.transition = "all ease 0.5s"
        document.getElementById("navbar").style.background = "none";
    }
}