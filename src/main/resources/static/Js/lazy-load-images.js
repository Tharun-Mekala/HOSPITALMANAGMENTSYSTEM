document.addEventListener("DOMContentLoaded", function () {
    const images = document.querySelectorAll("img[loading='lazy']");

    const options = {
        rootMargin: "0px",
        threshold: 0.1 // Adjust this threshold as needed
    };

    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                const image = entry.target;
                image.src = image.dataset.src;
                image.removeAttribute("loading");
                imageObserver.unobserve(image);
            }
        });
    }, options);

    images.forEach((image) => {
        imageObserver.observe(image);
    });
});