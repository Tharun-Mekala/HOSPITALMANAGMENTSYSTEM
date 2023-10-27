// Get all the "View Prescription" buttons and prescription modals
var viewPrescriptionButtons = document.querySelectorAll(".view-prescription-button");
var prescriptionModals = document.querySelectorAll(".prescription-modal");

// Function to open the prescription modal
function openPrescriptionModal(modal) {
	  modal.style.display = "block";
}

// Function to close the prescription modal
function closePrescriptionModal(modal) {
    modal.style.display = "none";
}

// Attach event listeners to each "View Prescription" button
viewPrescriptionButtons.forEach(function(button, index) {
    button.addEventListener("click", function() {
        openPrescriptionModal(prescriptionModals[index]);
    });
});

// Attach event listeners to each "Close" button
var closeButtons = document.querySelectorAll(".close-button");
closeButtons.forEach(function(button, index) {
    button.addEventListener("click", function() {
        closePrescriptionModal(prescriptionModals[index]);
    });
});

function disableBackButton() {
    window.history.forward();
 }
 window.onload = disableBackButton;
 window.onpageshow = function(evt) {
    if (evt.persisted) {
      disableBackButton();
    }
 };