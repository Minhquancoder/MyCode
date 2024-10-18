package Person;

import java.time.LocalDate;

public class Booking implements Comparable<Booking> {
    private String bookingId;
    private LocalDate bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String customerId;
    private String serviceCode;

    public Booking(String bookingId, LocalDate bookingDate, LocalDate startDate, LocalDate endDate, String customerId, String serviceCode) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.serviceCode = serviceCode;
    }

    public String getBookingId() {
        return bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    @Override
    public int compareTo(Booking other) {
        if (this.bookingDate.equals(other.bookingDate)) {
            return this.startDate.compareTo(other.startDate); // Sắp xếp theo ngày bắt đầu nếu ngày booking trùng
        }
        return this.bookingDate.compareTo(other.bookingDate); // Sắp xếp theo ngày booking
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", bookingDate=" + bookingDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerId='" + customerId + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId.equals(booking.bookingId);
    }

    @Override
    public int hashCode() {
        return bookingId.hashCode();
    }
}
