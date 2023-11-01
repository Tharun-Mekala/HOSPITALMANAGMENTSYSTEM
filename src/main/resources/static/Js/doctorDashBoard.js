document.addEventListener("DOMContentLoaded", function () {
    // Function to open the prescription modal
    function openPrescriptionModal(button) {
        const modal = button.parentNode.querySelector(".prescription-modal");
        modal.style.display = "flex";
    }

    // Function to close the prescription modal
    function closePrescriptionModal(modal) {
        modal.style.display = "none";
    }

    // Add event listeners to the "View Prescription" buttons
    const viewPrescriptionButtons = document.querySelectorAll(".view-prescription-button");
    viewPrescriptionButtons.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            openPrescriptionModal(button);
        });
    });

    // Add event listeners to the "Close" buttons
    const closeModals = document.querySelectorAll(".close-modal");
    closeModals.forEach((closeModal) => {
        closeModal.addEventListener("click", function (event) {
            event.preventDefault();
            const modal = closeModal.closest(".prescription-modal");
            closePrescriptionModal(modal);
        });
    });

    // Hide all prescription modals initially
    const prescriptionModals = document.querySelectorAll(".prescription-modal");
    prescriptionModals.forEach((modal) => {
        modal.style.display = "none";
    });
});

function disableBackButton() {
    window.history.forward();
}
window.onload = disableBackButton;
window.onpageshow = function (evt) {
    if (evt.persisted) {
        disableBackButton();
    }
};

// Function to switch tabs
const tabToggles = document.querySelectorAll(".tab-toggle");
const tabContents = document.querySelectorAll(".tab-content");

tabToggles.forEach((toggle, index) => {
    toggle.addEventListener("click", function () {
        // Hide all tab contents
        tabContents.forEach((content) => {
            content.style.display = "none";
        });

        // Show the clicked tab content
        tabContents[index].style.display = "block";
    });
});
