function toggleRegisterButton() {
    const checkbox = document.getElementById("agree");
    const registerBtn = document.getElementById("registerBtn");

    registerBtn.disabled = !checkbox.checked;
}