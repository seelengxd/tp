package seedu.address.model.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.commission.Commission;
import seedu.address.model.commission.CompletionStatus;
import seedu.address.model.commission.Deadline;
import seedu.address.model.commission.Fee;
import seedu.address.model.commission.Title;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.iteration.Date;
import seedu.address.model.iteration.Feedback;
import seedu.address.model.iteration.Iteration;
import seedu.address.model.iteration.IterationDescription;
import seedu.address.model.tag.Tag;
import seedu.address.storage.Storage;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Random random = new Random();
    public static Customer[] getSampleCustomers(Storage storage) {
        Customer[] customers = new Customer[] {
            new Customer.CustomerBuilder(new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), getTagSet("friends"))
                    .setAddress(new Address("Blk 30 Geylang Street 29, #06-40")).build(),
            new Customer.CustomerBuilder(new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), getTagSet("colleagues", "friends"))
                    .setAddress(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")).build(),
            new Customer.CustomerBuilder(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), getTagSet("neighbours"))
                    .setAddress(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")).build(),
            new Customer.CustomerBuilder(new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"), getTagSet("family"))
                    .setAddress(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")).build(),
            new Customer.CustomerBuilder(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), getTagSet("classmates"))
                    .setAddress(new Address("Blk 47 Tampines Street 20, #17-35")).build(),
            new Customer.CustomerBuilder(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), getTagSet("colleagues"))
                    .setAddress(new Address("Blk 45 Aljunied Street 85, #11-31")).build()
        };

        String placeholderImagePath = System.getProperty("user.dir") + "/src/main/resources/images/placeholderart.png";
        try {
            FileInputStream fis = new FileInputStream(placeholderImagePath);
            BufferedImage placeholderImage = ImageIO.read(fis);
            for (Customer customer: customers) {
                for (int i = 1; i <= 3; i++) {
                    Commission commission = new Commission.CommissionBuilder(
                            new Title(customer.getName().fullName + " Commission " + (i + 1)),
                            new Fee(random.nextDouble() * 20),
                            new Deadline(LocalDate.now()),
                            new CompletionStatus(random.nextBoolean()),
                            new HashSet<>()).build(customer);
                    for (int j = 1; j <= 3; j++) {
                        Path imageCopyPath = storage.getRandomImagePath();
                        storage.saveImage(placeholderImage, imageCopyPath);
                        Iteration iteration = new Iteration(
                                new Date(LocalDate.now()),
                                new IterationDescription("iteration description " + j),
                                imageCopyPath,
                                new Feedback("feedback " + j)
                        );
                        commission.addIteration(iteration);
                    }
                    customer.addCommission(commission);
                }
            }
        } catch (IOException e) {
            // shouldn't happen
            System.out.println("Failed to load placeholder image.");
            e.printStackTrace();
        }

        return customers;
    }

    public static ReadOnlyAddressBook getSampleAddressBook(Storage storage) {
        AddressBook sampleAb = new AddressBook();
        for (Customer sampleCustomer : getSampleCustomers(storage)) {
            sampleAb.addCustomer(sampleCustomer);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
