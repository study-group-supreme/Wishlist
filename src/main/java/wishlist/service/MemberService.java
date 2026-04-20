package wishlist.service;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.DuplicateMemberException;
import wishlist.exception.NotFoundException;
import wishlist.model.Member;
import wishlist.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * TODO: Add error handling:
     * - Validate id > 0
     * - Catch EmptyResultDataAccessException and convert to NotFoundException
     */
    public Member getById(int id) {
        return memberRepository.findById(id);
    }

    /**
     * TODO: Add validation:
     * - username cannot be null/blank
     * - Catch repository exceptions and convert to NotFoundException
     */
    public Member getByUsername(String username) {
        try {
            if (username != null) {
                return memberRepository.findByUsername(username);
            }
        } catch (Exception e) {
            throw new NotFoundException("No account with this username found: " + username);
        }
        return null;
    }

    /**
     * TODO: Add validation:
     * - email cannot be null/blank
     * - email must contain '@'
     * - Catch repository exceptions and convert to NotFoundException
     */
    public Member getByEmail(String email) {
        try {
            if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            }
            return memberRepository.findByEmail(email);
        } catch (Exception e) {
            throw new NotFoundException("No accout with this email found " + email);
        }
    }

    /**
     * TODO: Add validation before inserting:
     * - username not blank
     * - password not blank
     * - email valid format
     * - name not blank
     * <p>
     * TODO: Catch SQL exceptions (duplicate username/email)
     * - Convert to BadRequestException with a friendly message
     */
    public Member create(Member member) {
        try {
            memberRepository.insertMember(member);
            return memberRepository.findByUsername(member.getUsername());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateMemberException(e.getMessage());
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("User creation failed", e);
        }
    }

    /**
     * TODO: Add validation:
     * - Same rules as create()
     * - Ensure member exists before updating
     * - Catch SQL exceptions and convert to BadRequestException
     */
    // TODO: For updateMember:
    // 1. Load existing member using getById()
    // 2. Validate username, email, password, name
    // 3. Apply only allowed fields (e.g., do NOT overwrite id)
    // 4. Save using memberRepository.updateMember(existing)
    // 5. Return the updated member
    //
    // Reason: Members have sensitive fields (username, email, password).
    // We must prevent accidental or malicious overwrites.
    public Member update(Member member) {
        memberRepository.updateMember(member);
        return memberRepository.findById(member.getId());
    }

    /**
     * TODO: Add error handling:
     * - Ensure member exists before deleting
     * - Convert repository exceptions into NotFoundException
     */
    public Member delete(int id) {
        Member deletedMember = memberRepository.findById(id);
        memberRepository.deleteById(id);
        return deletedMember;
    }

    /**
     * TODO: Add validation:
     * - username not blank
     * - password not blank
     * <p>
     * TODO: Add error handling:
     * - If findByUsername throws, convert to NotFoundException
     * - If password mismatch, return null or throw BadRequestException
     * <p>
     * TODO: Consider hashing passwords (future improvement)
     */
    public Member login(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member != null && member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }
}
