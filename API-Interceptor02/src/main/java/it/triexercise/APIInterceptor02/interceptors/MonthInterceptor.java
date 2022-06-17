package it.triexercise.APIInterceptor02.interceptors;

import it.triexercise.APIInterceptor02.entity.Month;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MonthInterceptor implements HandlerInterceptor {
    public static List<Month> months = new ArrayList<>(Arrays.asList(
        new Month(1, "January", "Gennaio", "Januar"),
        new Month(3, "March", "Marzo", "Marz"),
        new Month(12, "December", "Dicembre", "Dezember"),
        new Month(10, "October", "Ottobre", "Oktober"),
        new Month(6, "June", "Giugno", "Juni"),
        new Month(4, "April", "Aprile", "April")
    ));


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userMonthNumber = request.getHeader("monthNumber");

        if (userMonthNumber == null) {
            response.setStatus(400);
        }

        int monthNumber = Integer.parseInt(userMonthNumber);
        Optional<Month> month = months.stream()
                .filter(singleMonth -> {
                    return singleMonth.getMonthNumber() == monthNumber;
                }).findFirst();

        if (month.isPresent()) {
            request.setAttribute("MonthInterceptor-month", month.get());
            return true;
        }
        request.setAttribute("MonthInterceptor-month", new Month(0, "nope", "nope", "nope"));
        return true;
    }
}
