// Function to open the prescription modal
function openPrescriptionModal(button) {
    const modal = button.nextElementSibling;
    modal.style.display = "block";
}

// Function to close the prescription modal
function closePrescriptionModal(button) {
    const modal = button.closest(".prescription-modal");
    modal.style.display = "none";
}

// Add event listeners to the "View Prescription" buttons
const viewPrescriptionButtons = document.querySelectorAll(".view-prescription-button");
viewPrescriptionButtons.forEach((button) => {
    button.addEventListener("click", function () {
        openPrescriptionModal(button);
    });
});

// Add event listeners to the "Close" buttons
const closeButtons = document.querySelectorAll(".close-button");
closeButtons.forEach((button) => {
    button.addEventListener("click", function () {
        closePrescriptionModal(button);
    });
});

// Hide all prescription modals initially
const prescriptionModals = document.querySelectorAll(".prescription-modal");
prescriptionModals.forEach((modal) => {
    modal.style.display = "none";
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

 
 function printPrescription() {
        var prescriptionContent = document.querySelector('.prescription-content').innerHTML;
        var myWindow = window.open('', 'PRINT', 'height=400,width=600');
       myWindow.document.write(`
     <!DOCTYPE html>
      <html>
        <head>
          <style>
            @media print {
              .prescription-content {
                background-color: lightblue;
                font-family: "Playfair Display", serif;
                color: black;
                margin: 150px 400px;
                padding: 20px;
                border-radius: 10px;
                text-align: center;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                max-height: 400px;
                overflow-y: scroll;
              }
              .prescription-content .info {
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-size: 20px;
              }
              .prescription-content p {
                font-size: 20px;
              }
              .prescription-content ul li {
                text-transform: capitalize;
                white-space: pre-line;
              }
            }
          </style>
        </head>
        <body>
          ${prescriptionContent}
        </body>
      </html>
    `);
        myWindow.document.close();
        myWindow.focus();
        myWindow.print();
        myWindow.close();
        return true;
    }