package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses user input into a RemarkCommand.
 */
public class RemarkCommandParser implements Parser {
    public static final Prefix PREFIX_REMARK = new Prefix("r/");

    /**
     * Parses RemarkCommand input into Command instance.
     * @param userInput String of 1 indexed input and r/remark.
     * @return RemarkCommand.
     * @throws ParseException If the user enters the wrong input.
     */
    @Override
    public Command parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_REMARK);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemarkCommand.MESSAGE_USAGE), ive);
        }
        Remark remark = new Remark(argMultimap.getValue(PREFIX_REMARK).orElse(""));
        return new RemarkCommand(index, remark);
    }
}
