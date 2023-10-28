package ee.lhv.aml.rest.dto.request;

import jakarta.annotation.Nonnull;

public record SanctionedPersonCheckRequest(@Nonnull String sl, @Nonnull String user) {

}
