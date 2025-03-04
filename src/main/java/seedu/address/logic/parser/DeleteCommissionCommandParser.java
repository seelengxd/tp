package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommissionCommand object.
 */
public class DeleteCommissionCommandParser implements Parser<DeleteCommissionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommissionCommand
     * and returns a DeleteCommissionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommissionCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommissionCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommissionCommand.MESSAGE_USAGE), pe);
        }
    }

}
