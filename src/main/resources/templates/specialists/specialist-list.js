fetch('/specialists')
    .then(response => response.json())
    .then(specialists => {
        let tableBody = document.getElementById('specialistTableBody');
        specialists.forEach(specialist => {
            tableBody.innerHTML += `
                <tr>
                    <td>${specialist.firstName}</td>
                    <td>${specialist.lastName}</td>
                    <td>${specialist.email}</td>
                    <td>${specialist.role}</td>
                </tr>
            `;
        });
    });