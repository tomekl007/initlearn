package com.stormpath.tutorial.utils;

import com.google.common.collect.Ordering;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.tutorial.group.GroupService;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.model.UserBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletRequest;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AccountUtils implements AccountFields {

    public static void addBioField(Account a, String bio) {
        addCustomFieldToAccount(a, BIO_FILED, bio);
    }

    public static void addLinksField(Account a, List<String> values) {
        addCustomListFieldToAccount(a, LINKS_FILED, values, a.getCustomData());
    }

    public static void addScreenheroField(Account a, String value) {
        addCustomFieldToAccount(a, SCREEN_HERO_FIELD, value);

    }

    public static void addHourRateForTeacher(Account a, Integer value) {
        addCustomIntegerField(a, HOUR_RATE_FIELD, value);

    }

    public static void addSkillsForTeacher(Account a, List<String> skills) {
        List<String> skillsNormalized = skills
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        addCustomListFieldToAccount(a, SKILLS_FIELD, skillsNormalized, a.getCustomData());
    }

    public static void addNumberOfRateForTeacher(Account a, Integer numberOfRates) {
        addCustomIntegerField(a, NUMBER_OF_RATES_FIELD, numberOfRates);
    }

    public static void addAverageForTeacher(Account a, Double average) {
        String newAv = convertDoubleToString(average);
        addCustomFieldToAccount(a, AVERAGE_FIELD, newAv);
    }

    public static String convertDoubleToString(Double average) {
        return String.valueOf(average);
    }

    public static void addImgField(Account a, String img) {
        addCustomFieldToAccount(a, IMG_FIELD, img);
    }

    public static void addLinkedInField(Account a, String link) {
        addCustomFieldToAccount(a, LINKEDIN_FIELD, link);
    }


    public static void addCustomListFieldToAccount(Account a, String fieldName, List<String> list,
                                                   Map<String, Object> customData) {
        Object o = customData.get(fieldName);
        List<String> userSkills = new LinkedList<>();
        if (o != null) {
            userSkills.addAll((List<String>) o);
        }
        userSkills.addAll(list);
        List<String> deduplicate =
                new ArrayList<>(new LinkedHashSet<>(userSkills));
        filterEmptyElements(deduplicate);
        customData.put(fieldName, deduplicate);
        a.save();
    }

    private static void filterEmptyElements(List<String> deduplicate) {
        deduplicate.removeIf(String::isEmpty);
    }

    public static void addCustomFieldToAccount(Account a, String name, Object value) {
        CustomData customData = a.getCustomData();
        customData.put(name, value);
        a.save();
    }

    public static void addCustomIntegerField(Account a, String name, Integer value) {
        CustomData customData = a.getCustomData();
        customData.put(name, value);
        a.save();
    }

    public static String getCustomFieldValue(Account a, String field) {
        Object o = a.getCustomData().get(field);
        if (o == null) {
            return "";
        } else {
            return (String) o;
        }
    }

    public static Double getCustomFieldValueAsDouble(Account a, String field) {
        Object o = a.getCustomData().get(field);
        if (o == null) {
            return null;
        } else {
            return convertStringToDouble((String) o);
        }

    }

    public static double convertStringToDouble(String o) {
        return Double.parseDouble(o);
    }

    public static Integer getCustomIntegerValue(Account a, String field) {
        Object o = a.getCustomData().get(field);
        if (o == null) {
            return null;
        } else {
            return (int) o;
        }
    }

    public static List<String> getCustomListFieldValue(Account a, String fieldName) {
        Object o = a.getCustomData().get(fieldName);
        if (o == null) {
            return Collections.emptyList();
        } else {
            return (List<String>) o;
        }
    }


    public static User mapAccountToUser(Account a) {
        boolean isATeacher = GroupService.isATeacher(a);
        return new UserBuilder()
                .setEmail(a.getEmail())
                .setFullName(a.getFullName())
                .setGivenName(a.getGivenName())
                .setMiddleName(a.getMiddleName())
                .setScreenHero(AccountUtils.getCustomFieldValue(a, SCREEN_HERO_FIELD))
                .setHourRate(AccountUtils.getCustomIntegerValue(a, HOUR_RATE_FIELD))
                .setLinkedIn(AccountUtils.getCustomFieldValue(a, LINKEDIN_FIELD))
                .setSkills(AccountUtils.getCustomListFieldValue(a, SKILLS_FIELD))
                .setLinks(AccountUtils.getCustomListFieldValue(a, LINKS_FILED))
                .setBio(AccountUtils.getCustomFieldValue(a, BIO_FILED))
                .setImg(AccountUtils.getCustomFieldValue(a, IMG_FIELD))
                .setAverage(AccountUtils.getCustomFieldValueAsDouble(a, AVERAGE_FIELD))
                .setNumberOfRates(AccountUtils.getCustomIntegerValue(a, NUMBER_OF_RATES_FIELD))
                .setIsATeacher(isATeacher)
                .setRatedBy(AccountUtils.getCustomListFieldValue(a, RATED_BY))
                .createUser();
    }


    public static List<User> mapToUsers(List<Account> list) {
        return list
                .stream()
                .map(AccountUtils::mapAccountToUser)
                .collect(Collectors.toList());
    }


    public static List<User> mapToUsers(AccountList list) {
        List<Account> accounts = new ArrayList<>();
        list.iterator().forEachRemaining(accounts::add);
        return mapToUsers(accounts);
    }

    public static Optional<User> getUserIfUserLoggedIn(ServletRequest servletRequest) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return Optional.of(mapAccountToUser(authenticatedAccount));
        } else {
            return Optional.empty();
        }
    }

    public static <T> ResponseEntity<T> actionForAuthenticatedUserOrUnauthorized(ServletRequest servletRequest, Function<Account, T> action) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return new ResponseEntity<>(action.apply(authenticatedAccount), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public static <T> ResponseEntity<T> actionResponseEntityForAuthenticatedUserOrUnauthorized(ServletRequest servletRequest,
                                                                                               Function<Account, ResponseEntity<T>> action) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            Account authenticatedAccount = AccountResolver.INSTANCE.getRequiredAccount(servletRequest);
            return action.apply(authenticatedAccount);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    public static void sortUsersBy(List<User> list, String sort, Integer sortOrder) {
        Comparator<User> userComparator = (User o1, User o2) -> {
            if (sort.equals(HOUR_RATE_FIELD)) {
                Integer hourRate1 = o1.hourRate == null ? 0 : o1.hourRate;
                Integer hourRate2 = o2.hourRate == null ? 0 : o2.hourRate;
                return hourRate1.compareTo(hourRate2);
            } else if (sort.equals(AVERAGE_FIELD)) {
                Double average1 = o1.average == null ? 0d : o1.average;
                Double average2 = o2.average == null ? 0d : o2.average;
                return average1.compareTo(average2);
            } else {
                return o1.givenName.compareTo(o2.givenName);
            }
        };

        if(sortOrder == -1){
            Collections.sort(list, Ordering.from(userComparator).reverse());
        }else{
            Collections.sort(list, Ordering.from(userComparator));
        }

    }

    public static void addRatedBy(Account a, Account userThatRate) {
        addCustomListFieldToAccount(a, RATED_BY,
                Collections.singletonList(userThatRate.getEmail()), a.getCustomData());
    }


    public static<T> List<T> addToList(Account account, T toAdd, String fieldName) {
        CustomData customData = account.getCustomData();

        List<T> list;
        Object reservations = customData.get(fieldName);
        if (reservations == null) {
            list = new LinkedList<>();
        } else {
            list = (List<T>) reservations;
        }
        list.add(toAdd);

        customData.put(fieldName, list);
        account.save();
        return list;
    }

}
