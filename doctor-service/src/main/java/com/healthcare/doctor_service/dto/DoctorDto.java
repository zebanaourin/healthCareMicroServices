package com.healthcare.doctor_service.dto;

import com.healthcare.doctor_service.domain.Doctor;
import com.healthcare.doctor_service.validators.ValueInList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record DoctorDto(
        String id,

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Phone is invalid")
        String phone,

        @NotBlank(message = "Address is required")
        String address,

        @ValueInList(allowedValues = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"},
                message = "Invalid operation day")
        String[] opDays,
        String[] specialization
) {
    public static List<DoctorDto> toDtos(List<Doctor> doctors) {
        return doctors.stream().map(DoctorDto::fromEntity).toList();
    }

    public static Doctor toEntity(DoctorDto doctorDto) {
        return Doctor.builder()
                .address(doctorDto.address())
                .email(doctorDto.email())
                .fullName(doctorDto.fullName())
                .opDays(doctorDto.opDays())
                .phone(doctorDto.phone())
                .specialization(doctorDto.specialization())
                .build();
    }

    public Doctor toDoctor() {
        return new Doctor(fullName, email, phone, address,
                specialization, opDays);
    }

    public static DoctorDto fromEntity(Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFullName(), doctor.getEmail(),
                doctor.getPhone(), doctor.getAddress(), doctor.getOpDays(),
                doctor.getSpecialization());
    }
}
